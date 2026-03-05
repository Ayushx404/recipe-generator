package com.ayush.recipegenerator.domain.repo

import com.ayush.recipegenerator.common.util.Resource
import com.ayush.recipegenerator.data.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepo {
    fun generateRecipe(prompt: String): Flow<Resource<Recipe>>

    fun getRecipes(): Flow<Resource<List<Recipe>>>

    fun getRecipe(id: Int): Flow<Resource<Recipe>>

    fun updateRecipe(id: Int,isSaved: Int) : Flow<Resource<Int>>

    fun getSavedRecipes(): Flow<Resource<List<Recipe>>>

    fun removeRecipe(recipe: Recipe): Flow<Resource<Int>>
}
