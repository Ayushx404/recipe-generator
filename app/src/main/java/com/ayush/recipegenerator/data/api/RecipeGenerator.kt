package com.ayush.recipegenerator.data.api

import com.ayush.recipegenerator.data.api.model.ApiRecipe

/**
 * RecipeGenerator interface defines a contract for generating recipes.
 */
interface RecipeGenerator {
    /**
     * Generates a recipe based on the provided prompt.
     *
     * @param prompt The prompt to generate the recipe from.
     * @return An [ApiRecipe] generated from the prompt, or null if an error occurs.
     */
    suspend fun generateRecipe(prompt: String): ApiRecipe?
}
