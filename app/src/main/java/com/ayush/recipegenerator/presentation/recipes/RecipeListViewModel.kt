package com.ayush.recipegenerator.presentation.recipes

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayush.recipegenerator.common.util.Resource
import com.ayush.recipegenerator.data.model.Recipe
import com.ayush.recipegenerator.domain.usecases.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {

    private var _recipeListState = mutableStateOf(RecipeListState())
    val recipeListState: State<RecipeListState> = _recipeListState

    init {
        getGenerateRecipes()
    }

    /**
     * Initiates the generation of recipes by invoking the use case.
     * It collects the results emitted by the use case and updates the UI state accordingly.
     */
    fun getGenerateRecipes() = viewModelScope.launch(Dispatchers.IO) {
        // Launches a coroutine in the ViewModel's scope for background processing
        // Calls the use case to generate recipes
        val generatedRecipes = useCase.generatedRecipes()

        // Collects the latest values emitted by the flow
        generatedRecipes.collectLatest { resource ->
            when (resource) {
                /**
                 * Handles the Error state by updating the HomeState with an empty list and a flag
                 * to indicate that no recipes were generated.
                 */
                is Resource.Error ->
                    updateRecipeListState(
                        _recipeListState.value.copy(
                            recipes = mutableListOf(),
                            showNoRecipeGenerated = true,
                            isLoading = false
                        ),
                    )

                /**
                 * Handles the Loading state by updating the HomeState with an empty list and a flag
                 * to indicate that no recipes were generated.
                 */
                is Resource.Loading ->
                    updateRecipeListState(
                        _recipeListState.value.copy(
                            recipes = mutableListOf(),
                            showNoRecipeGenerated = false,
                            isLoading = true
                        ),
                    )

                /**
                 * Handles the Success state by updating the HomeState with the generated recipes
                 * and resetting the flag to indicate that recipes were successfully generated.
                 *
                 * @param resource.data The list of generated recipes, or an empty list if data is null.
                 */
                is Resource.Success ->
                    updateRecipeListState(
                        _recipeListState.value.copy(
                            recipes = resource.data ?: mutableListOf(),
                            showNoRecipeGenerated = false,
                            isLoading = false
                        ),
                    )
            }
        }
    }


    /**
     * Updates the RecipeListState with the given state.
     *
     * @param recipeListState The new state to update.
     */
    private fun updateRecipeListState(recipeListState: RecipeListState) {
        _recipeListState.value = recipeListState
    }

    fun deleteRecipe(recipe:Recipe) = viewModelScope.launch {
        useCase.removeRecipe(recipe).collectLatest { resource ->
            when(resource) {
                is Resource.Error -> Log.e(TAG,"Error deleting recipe: ${resource.message}")
                is Resource.Loading -> Log.d(TAG,"Deleting...")
                is Resource.Success -> Log.d(TAG,"Recipe deleted successfully")
            }
        }
    }

    companion object {
        private const val TAG = "RecipeListViewModel"
    }
}