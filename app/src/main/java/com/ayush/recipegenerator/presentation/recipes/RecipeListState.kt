package com.ayush.recipegenerator.presentation.recipes

import com.ayush.recipegenerator.data.model.Recipe
import com.ayush.recipegenerator.presentation.recipe_detail.RecipeDetailState


data class RecipeListState(
    val recipes: List<Recipe> = mutableListOf(),
    val error: String = "",
    val isLoading: Boolean = false,
    val showNoRecipeGenerated: Boolean = true,
    ) {
    companion object {
        val INVALID_RECIPE_LIST_STATE = RecipeDetailState()
    }
}
