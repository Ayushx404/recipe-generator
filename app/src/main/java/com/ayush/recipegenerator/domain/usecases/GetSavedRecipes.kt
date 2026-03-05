package com.ayush.recipegenerator.domain.usecases

import com.ayush.recipegenerator.domain.repo.RecipeRepo

class GetSavedRecipes(
    private val recipeRepo: RecipeRepo
) {
    suspend operator fun invoke() = recipeRepo.getSavedRecipes()
}