package com.ayush.recipegenerator.presentation.recipe_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayush.recipegenerator.common.util.Resource
import com.ayush.recipegenerator.data.model.Recipe
import com.ayush.recipegenerator.domain.usecases.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {

    private var _recipeDetailState = mutableStateOf(RecipeDetailState())
    val recipeDetailState: State<RecipeDetailState> = _recipeDetailState


    fun getRecipe(id: Int) = viewModelScope.launch {
        val recipeResource = useCase.getRecipeById(id)
        recipeResource.collectLatest { resource ->
            when (resource) {
                is Resource.Loading -> updateRecipeState(_recipeDetailState.value.copy(isLoading = true))
                is Resource.Error -> updateRecipeState(
                    _recipeDetailState.value.copy(
                        isLoading = false,
                        recipe = Recipe.INVALID_RECIPE,
                        error = resource.message ?: "",
                    )
                )

                is Resource.Success -> updateRecipeState(
                    _recipeDetailState.value.copy(
                        isLoading = false,
                        recipe = resource.data ?: Recipe.INVALID_RECIPE,
                        isRecipeSaved = resource.data?.isSaved == 1
                    )
                )
            }
        }
    }

    fun saveRecipe(id: Int,isSaved: Boolean) = viewModelScope.launch {
        val save = if(isSaved) 0 else 1
        val updateRecipeResource = useCase.saveRecipe(recipeId = id, isSaved = save)
        updateRecipeResource.collectLatest { resource ->
            when (resource) {
                is Resource.Loading -> updateRecipeState(_recipeDetailState.value.copy(isLoading = true))
                is Resource.Error -> updateRecipeState(
                    _recipeDetailState.value.copy(
                        isLoading = false,
                        recipe = Recipe.INVALID_RECIPE,
                        error = resource.message ?: "",
                        isRecipeSaved = false
                    )
                )

                is Resource.Success -> updateRecipeState(
                    _recipeDetailState.value.copy(
                        isLoading = false,
                        isRecipeSaved = save == 1
                    )
                )
            }
        }
    }



    private fun updateRecipeState(recipeDetailState: RecipeDetailState) {
        _recipeDetailState.value = recipeDetailState
    }

    fun resetRecipeState() {
        _recipeDetailState.value = RecipeDetailState.INVALID_HOME_STATE
    }
}