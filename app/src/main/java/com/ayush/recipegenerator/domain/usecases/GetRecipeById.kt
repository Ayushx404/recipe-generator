package com.ayush.recipegenerator.domain.usecases

import com.ayush.recipegenerator.domain.repo.RecipeRepo

class GetRecipeById(
    private val recipeRepo: RecipeRepo
)  {
    suspend operator fun invoke(recipeId: Int) = recipeRepo.getRecipe(recipeId)
}