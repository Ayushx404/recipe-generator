package com.ayush.recipegenerator.domain.usecases

/**
 * Represents a use cases unit within the application.
 *
 * @property generateRecipe The use case responsible for generating recipes.
 */
data class UseCase(
    val generateRecipe: GenerateRecipe,
    val getStaticIngredient: GetStaticIngredient,
    val generatedRecipes: GetGeneratedRecipes,
    val getRecipeById: GetRecipeById,
    val saveRecipe: SaveRecipe,
    val getSavedRecipes: GetSavedRecipes,
    val removeRecipe: RemoveRecipe
)
