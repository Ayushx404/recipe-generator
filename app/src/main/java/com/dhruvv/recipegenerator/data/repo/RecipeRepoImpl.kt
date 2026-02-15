package com.dhruvv.recipegenerator.data.repo

import android.util.Log
import com.dhruvv.recipegenerator.common.util.Resource
import com.dhruvv.recipegenerator.data.api.RecipeGenerator
import com.dhruvv.recipegenerator.data.api.model.toRecipeEntity
import com.dhruvv.recipegenerator.data.db.dao.RecipeDao
import com.dhruvv.recipegenerator.data.db.entities.toRecipeUIModel
import com.dhruvv.recipegenerator.data.model.Recipe
import com.dhruvv.recipegenerator.data.model.Recipe.Companion.toRecipeEntity
import com.dhruvv.recipegenerator.domain.repo.RecipeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * RecipeRepoImpl is responsible for providing access to recipe data, including generation and retrieval.
 *
 * @param recipeDao An instance of [RecipeDao] for database access.
 * @param recipeGenerator An instance of [RecipeGenerator] for recipe generation.
 */
class RecipeRepoImpl(
    private val recipeDao: RecipeDao,
    private val recipeGenerator: RecipeGenerator,
) : RecipeRepo {
    /**
     * Generates a recipe based on the provided prompt.
     *
     * @param prompt The prompt to generate the recipe from.
     * @return A flow emitting [Resource] objects representing the state of the recipe generation process.
     */
    override fun generateRecipe(prompt: String): Flow<Resource<Recipe>> {
        return flow {
            emit(Resource.Loading())
            try {
                Log.i(TAG, "generateRecipe called with prompt: $prompt")
                val response = recipeGenerator.generateRecipe(prompt)
                Log.i(TAG, "generateRecipe response: $response")
                response?.let { apiRecipe ->
                    val recipeEntity = apiRecipe.toRecipeEntity()
                    val recipeId = recipeDao.insertRecipe(recipeEntity)
                    emit(Resource.Success(recipeEntity.apply {
                      this.id = recipeId.toInt()
                    }.toRecipeUIModel()))
                }

                if (response == null) {
                    Log.e(TAG, "generateRecipe response is null")
                    emit(Resource.Error("Oops! Unable to generate the recipe.😞 Try again!"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "generateRecipe exception: ${e.message}", e)
                emit(Resource.Error(e.message))
            }
        }
    }

    /**
     * Retrieves all recipes from the database.
     *
     * @return A flow emitting [Resource] objects representing the state of the retrieval process.
     */
    override fun getRecipes(): Flow<Resource<List<Recipe>>> {
        return flow {
            emit(Resource.Loading())
            try {
                // Fetch recipes from the database and transform them to UI models
                val recipeEntityFlow = recipeDao.getRecipes() // Flow<List<RecipeEntity>>
                // Map the RecipeEntity objects to RecipeUIModel objects
                val recipesFlow = recipeEntityFlow.map { recipeEntities ->
                    recipeEntities.map { it.toRecipeUIModel() }
                }

                // Collect the flow of RecipeUIModel objects and emit a Resource state
                recipesFlow.collect { recipes ->
                    if (recipes.isNotEmpty()) {
                        // Emit Success state with the list of recipes
                        emit(Resource.Success(recipes))
                    } else {
                        // Emit Error state with a message
                        emit(Resource.Error("Oops, we couldn't find any recipes!️"))
                    }
                }

            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }
        }
    }

    /**
     * Retrieves a recipe by its ID from the database.
     *
     * @param id The ID of the recipe to retrieve.
     * @return A flow emitting [Resource] objects representing the state of the retrieval process.
     */
    override fun getRecipe(id: Int): Flow<Resource<Recipe>> {
        return flow {
            emit(Resource.Loading())
            try {
                val recipe = recipeDao.getRecipeById(id)?.toRecipeUIModel()
                if (recipe != null) {
                    emit(Resource.Success(recipe))
                } else {
                    emit(Resource.Error("Uh-oh, we couldn't find that recipe!🍲"))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }
        }
    }


    override fun updateRecipe(id: Int,isSaved: Int): Flow<Resource<Int>> {
        return flow {
            emit(Resource.Loading())
            try {
                val rowsAffected = recipeDao.updateRecipe(id, isSaved)
                if (rowsAffected != null && rowsAffected != 0) {
                    emit(Resource.Success(rowsAffected))
                } else {
                    emit(Resource.Error("Uh-oh, we failed to save that recipe!🍲"))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }
        }
    }

    override fun getSavedRecipes(): Flow<Resource<List<Recipe>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val recipes = recipeDao.getSavedRecipes()
                if (recipes.isNotEmpty()) {
                    emit(Resource.Success(recipes.map { it.toRecipeUIModel() }))
                } else {
                    emit(Resource.Error("Oops, we couldn't find any saved recipes!🍽️"))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }
        }
    }

    override fun removeRecipe(recipe: Recipe): Flow<Resource<Int>> {
        return flow {
            emit(Resource.Loading())
            try {
                val rowsAffected = recipeDao.deleteExpense(recipe.toRecipeEntity())
                if (rowsAffected != 0) {
                    emit(Resource.Success(rowsAffected))
                } else {
                    emit(Resource.Error("Uh-oh, we failed to delete that recipe!🍲"))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }
        }
    }

    companion object {
        private const val TAG = "RecipeRepoImpl"
    }
}
