package com.ayush.recipegenerator.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayush.recipegenerator.common.util.Resource
import com.ayush.recipegenerator.data.model.Recipe
import com.ayush.recipegenerator.domain.usecases.UseCase
import com.ayush.recipegenerator.presentation.home.HomeState.Companion.INVALID_HOME_STATE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * HomeViewModel is a ViewModel class responsible for managing the state
 * related to the Home screen of the recipe generator application.
 * It uses a use case to generate recipes and updates the UI state accordingly.
 *
 * @param useCase The use case responsible for generating recipes.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    // Injects the UseCase dependency into the ViewModel
    private val useCase: UseCase
) : ViewModel() {

    // Private mutable state to hold the HomeState, initialized to INVALID_HOME_STATE
    private var _homeState = mutableStateOf(INVALID_HOME_STATE)

    // Public immutable state exposed to the UI
    val homeState: State<HomeState> = _homeState


    init {
        getGenerateRecipes()
    }

    /**
     * Initiates the generation of recipes by invoking the use case.
     * It collects the results emitted by the use case and updates the UI state accordingly.
     */
    private fun getGenerateRecipes() = viewModelScope.launch(Dispatchers.IO) {
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
                    updateHomeState(
                        _homeState.value.copy(
                            generatedRecipes = mutableListOf(),
                            showNoRecipeGenerated = true,
                            isLoading = false
                        ),
                    )

                /**
                 * Handles the Loading state by updating the HomeState with an empty list and a flag
                 * to indicate that no recipes were generated.
                 */
                is Resource.Loading ->
                    updateHomeState(
                        _homeState.value.copy(
                            generatedRecipes = mutableListOf(),
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
                    updateHomeState(
                        _homeState.value.copy(
                            generatedRecipes = resource.data ?: mutableListOf(),
                            showNoRecipeGenerated = false,
                            isLoading = false
                        ),
                    )
            }
        }
    }

    /**
     * Updates the HomeState with the given state.
     *
     * @param homeState The new state to update.
     */
    private fun updateHomeState(homeState: HomeState) {
        _homeState.value = homeState
    }

    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch {
        useCase.removeRecipe(recipe).collectLatest { resource ->
            when(resource) {
                is Resource.Error -> Log.e(TAG,"Error deleting recipe: ${resource.message}")
                is Resource.Loading -> Log.d(TAG,"Deleting...")
                is Resource.Success -> Log.d(TAG,"Recipe deleted successfully")
            }
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }

}