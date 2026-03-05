package com.ayush.recipegenerator.domain.usecases

import com.ayush.recipegenerator.domain.repo.RecipeRepo

/**
 * A use case class that handles the operation of getting generated recipes
 * from the RecipeRepo.
 *
 * This class provides an operator function that, when invoked, calls the
 * getRecipes method of the RecipeRepo to retrieve the recipes.
 *
 * @property recipeRepo The repository that provides access to the recipe data.
 */
class GetGeneratedRecipes constructor(
    private val recipeRepo: RecipeRepo // The repository instance used to fetch recipes
) {
    /**
     * Operator function to invoke the recipe generation.
     *
     * This function delegates the call to the getRecipes method of the
     * RecipeRepo to obtain the list of recipes.
     *
     * @return The list of recipes from the repository.
     */
    operator fun invoke() = recipeRepo.getRecipes()
}
