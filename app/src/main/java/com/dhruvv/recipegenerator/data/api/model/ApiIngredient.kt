package com.dhruvv.recipegenerator.data.api.model

import com.squareup.moshi.Json

/**
 * Represents an ingredient used in a recipe, structured for API communication.
 *
 * @property image_url The URL of the ingredient's image (ignored during JSON serialization).
 * @property name The name of the ingredient.
 * @property preparation Describes how the ingredient should be prepared (e.g., chopped, diced).
 *                        Default is an empty string if not specified.
 * @property quantity The quantity of the ingredient required for the recipe (e.g., "1 cup", "2 tablespoons").
 * @property unit The unit of measurement for the quantity (e.g., cup, tablespoon).
 */
data class ApiIngredient(
    @Json(ignore = true)
    val image_url: String = "",
    val name: String = "",
    val preparation: String = "",
    val quantity: String = "",
    val unit: String = "",
)
