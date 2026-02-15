package com.dhruvv.recipegenerator.data.api.gemini

import android.util.Log
import com.dhruvv.recipegenerator.BuildConfig
import com.dhruvv.recipegenerator.data.api.RecipeGenerator
import com.dhruvv.recipegenerator.data.api.model.ApiRecipe
import com.dhruvv.recipegenerator.data.api.model.ApiRecipeMain
import com.dhruvv.recipegenerator.data.api.rest.GroqApiService
import com.dhruvv.recipegenerator.data.api.rest.GroqMessage
import com.dhruvv.recipegenerator.data.api.rest.GroqRequest
import com.dhruvv.recipegenerator.data.api.rest.GroqResponseFormat
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class GroqRecipeGenerator : RecipeGenerator {

    private val apiService: GroqApiService by lazy {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("GroqAPI", message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${BuildConfig.GROQ_API_KEY}")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.groq.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()

        retrofit.create(GroqApiService::class.java)
    }

    override suspend fun generateRecipe(prompt: String): ApiRecipe? {
        Log.i(TAG, "Starting recipe generation with prompt: $prompt")
        Log.i(TAG, "Using Groq API key: ${BuildConfig.GROQ_API_KEY.take(10)}...")

        return try {
            val fullPrompt = buildPrompt(prompt)

            val request = GroqRequest(
                model = MODEL,
                messages = listOf(
                    GroqMessage(
                        role = "user",
                        content = fullPrompt
                    )
                ),
                temperature = 0.7,
                maxTokens = 4096,
                responseFormat = GroqResponseFormat()
            )

            Log.i(TAG, "Sending request to Groq API...")

            val response = apiService.generateContent(request)

            if (response.error != null) {
                Log.e(TAG, "API Error: ${response.error.message} (code: ${response.error.code})")
                throw Exception(response.error.message ?: "API error occurred")
            }

            val responseText = response.choices
                ?.firstOrNull()
                ?.message
                ?.content

            Log.i(TAG, "Raw response: ${responseText?.take(500)}...")

            if (responseText.isNullOrBlank()) {
                Log.e(TAG, "Empty response from API")
                return null
            }

            parseRecipeResponse(responseText)

        } catch (e: Exception) {
            Log.e(TAG, "Error generating recipe: ${e.message}", e)
            null
        }
    }

    private fun buildPrompt(prompt: String): String {
        return """
$INITIAL_INSTRUCTION

Ingredients: $prompt

$OUTPUT_INSTRUCTION
        """.trimIndent()
    }

    private fun parseRecipeResponse(responseText: String): ApiRecipe? {
        return try {
            val cleanJson = responseText
                .trim()
                .removePrefix("```json")
                .removePrefix("```")
                .removeSuffix("```")
                .trim()

            Log.i(TAG, "Cleaned JSON: ${cleanJson.take(500)}...")

            val gson = Gson()
            val apiRecipeMain = gson.fromJson(cleanJson, ApiRecipeMain::class.java)

            if (apiRecipeMain == null) {
                Log.e(TAG, "Failed to parse JSON response")
                return null
            }

            Log.i(TAG, "Successfully parsed recipe: ${apiRecipeMain.recipe.name}")
            apiRecipeMain.recipe

        } catch (e: Exception) {
            Log.e(TAG, "Error parsing recipe: ${e.message}", e)
            null
        }
    }

    companion object {
        private const val TAG = "GroqRecipeGenerator"
    }
}
