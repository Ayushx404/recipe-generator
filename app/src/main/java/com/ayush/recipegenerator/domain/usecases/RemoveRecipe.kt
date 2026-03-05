package com.ayush.recipegenerator.domain.usecases

import com.ayush.recipegenerator.data.model.Recipe
import com.ayush.recipegenerator.domain.repo.RecipeRepo

class RemoveRecipe(
    private val recipeRepo: RecipeRepo
) {
    suspend operator fun invoke(recipe: Recipe) =
        recipeRepo.removeRecipe(recipe)
}