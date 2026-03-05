package com.ayush.recipegenerator.presentation.saved_recipes

import com.ayush.recipegenerator.data.model.Recipe

data class SavedRecipeState(
    val recipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
    val showNoRecipeGenerated: Boolean = false
) {
    companion object {
        val INVALID_SAVED_RECIPE_STATE = SavedRecipeState()
    }
}
