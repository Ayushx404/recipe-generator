package com.ayush.recipegenerator.data.db.converters

import android.util.Log
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.ayush.recipegenerator.common.parser.JsonParser
import com.ayush.recipegenerator.data.api.model.ApiRecipe
import javax.inject.Inject

@ProvidedTypeConverter
class RecipeConverter
    @Inject
    constructor(
        private val jsonParser: JsonParser,
    ) {
        // Converts a Recipe object to its JSON string representation
        @TypeConverter
        fun fromRecipeToJsonString(apiRecipe: ApiRecipe): String {
            try {
                // Use the JsonParser to convert the Recipe object to a JSON string
                return jsonParser.toJson(apiRecipe, ApiRecipe::class.java).toString()
            } catch (e: Exception) {
                // Log any exceptions that occur during conversion
                Log.e(TAG, "fromRecipeToJsonString: $e")
            }
            // Return an empty string if conversion fails
            return ""
        }

        // Converts a JSON string representation of a Recipe back into a Recipe object
        @TypeConverter
        fun fromJsonStringToRecipe(jsonString: String): ApiRecipe {
            try {
                // Use the JsonParser to convert the JSON string back to a Recipe object
                return jsonParser.fromJson(jsonString, ApiRecipe::class.java) ?: ApiRecipe.INVALID_API_RECIPE
            } catch (e: Exception) {
                // Log any exceptions that occur during conversion
                Log.e(TAG, "fromJsonStringToRecipe: $e")
            }
            // Return an INVALID_RECIPE if conversion fails
            return ApiRecipe.INVALID_API_RECIPE
        }

        companion object {
            // Tag used for logging
            private const val TAG = "RecipeConverter"
        }
    }
