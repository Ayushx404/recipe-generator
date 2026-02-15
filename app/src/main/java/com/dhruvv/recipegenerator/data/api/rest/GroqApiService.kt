package com.dhruvv.recipegenerator.data.api.rest

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST

interface GroqApiService {

    @POST("openai/v1/chat/completions")
    suspend fun generateContent(
        @Body request: GroqRequest
    ): GroqResponse
}

data class GroqRequest(
    @SerializedName("model")
    val model: String,
    @SerializedName("messages")
    val messages: List<GroqMessage>,
    @SerializedName("temperature")
    val temperature: Double = 0.7,
    @SerializedName("max_tokens")
    val maxTokens: Int = 4096,
    @SerializedName("response_format")
    val responseFormat: GroqResponseFormat? = null
)

data class GroqMessage(
    @SerializedName("role")
    val role: String,
    @SerializedName("content")
    val content: String
)

data class GroqResponseFormat(
    @SerializedName("type")
    val type: String = "json_object"
)

data class GroqResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("choices")
    val choices: List<GroqChoice>?,
    @SerializedName("error")
    val error: GroqError?
)

data class GroqChoice(
    @SerializedName("index")
    val index: Int?,
    @SerializedName("message")
    val message: GroqMessage?,
    @SerializedName("finish_reason")
    val finishReason: String?
)

data class GroqError(
    @SerializedName("message")
    val message: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("code")
    val code: String?
)
