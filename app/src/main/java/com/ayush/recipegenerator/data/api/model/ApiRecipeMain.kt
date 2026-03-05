package com.ayush.recipegenerator.data.api.model

/**
 * Represents the main structure for an API response containing recipe details.
 *
 * @property recipe The main recipe object containing detailed information about the recipe.
 */
data class ApiRecipeMain(
    val recipe: ApiRecipe,
)
