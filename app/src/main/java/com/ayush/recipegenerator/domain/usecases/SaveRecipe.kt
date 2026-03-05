package com.ayush.recipegenerator.domain.usecases

import com.ayush.recipegenerator.domain.repo.RecipeRepo

class SaveRecipe(
    private val recipeRepo: RecipeRepo
) {
    suspend operator fun invoke(recipeId: Int, isSaved: Int) =
        recipeRepo.updateRecipe(id = recipeId, isSaved = isSaved)
}