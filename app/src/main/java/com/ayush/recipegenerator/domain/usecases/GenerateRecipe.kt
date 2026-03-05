package com.ayush.recipegenerator.domain.usecases

import com.ayush.recipegenerator.domain.repo.RecipeRepo

/**
 * Use case responsible for generating a recipe based on provided data.
 *
 * This class encapsulates the business logic for generating a recipe using the injected [RecipeRepo].
 *
 * @property recipeRepo The repository interface responsible for accessing recipe generation functionality.
 */
class GenerateRecipe(private val recipeRepo: RecipeRepo) {
    /**
     * Invokes the use case to generate a recipe based on the provided data.
     *
     * @param data The data required for generating the recipe. The specific format and content of 'data' depend on the implementation in [RecipeRepo].
     * @return The result of generating the recipe, typically encapsulated in the return type of [RecipeRepo.generateRecipe].
     */
    operator fun invoke(data: String) = recipeRepo.generateRecipe(data)
}
