package com.ayush.recipegenerator.presentation.generate_recipe

import com.ayush.recipegenerator.data.model.Recipe

/**
 * Represents the state of a recipe generation process.
 *
 * This data class encapsulates the current state of generating a recipe, including whether
 * it's currently loading, the generated recipe itself, and any error message if applicable.
 *
 * @property isLoading Indicates whether the recipe generation process is currently ongoing.
 *                    Defaults to false when initialized.
 * @property generatedRecipe The recipe that has been generated. Defaults to [Recipe.INVALID_RECIPE].
 * @property isErrorMessage An optional error message string if there was an error during recipe generation.
 *                          Defaults to an empty string when initialized.
 */
data class GenerateRecipeState(
    var isLoading: Boolean = false,
    var generatedRecipe: Recipe = Recipe.INVALID_RECIPE,
    var isErrorMessage: String = "",
) {
    companion object {
        /**
         * Represents an invalid or default instance of [GenerateRecipeState].
         * This can be used, for example, to initialize placeholder or default state objects.
         */
        val INVALID_GENERATE_RECIPE_STATE = GenerateRecipeState()
    }
}
