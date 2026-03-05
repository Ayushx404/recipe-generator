package com.ayush.recipegenerator.presentation.generate_recipe

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayush.recipegenerator.common.util.Resource
import com.ayush.recipegenerator.data.model.CheckableItem
import com.ayush.recipegenerator.data.model.Recipe
import com.ayush.recipegenerator.domain.usecases.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

/**
 * ViewModel responsible for managing the state and business logic related to recipe generation.
 *
 * @param useCase The use case responsible for generating recipes.
 */
@HiltViewModel
class GenerateRecipeViewModel
@Inject
constructor(
    private val useCase: UseCase,
) : ViewModel() {
    // Mutable state for holding the current generate recipe state, initialized with INVALID_GENERATE_RECIPE_STATE.
    private var _generateRecipeState =
        mutableStateOf(GenerateRecipeState.INVALID_GENERATE_RECIPE_STATE)
    val generateRecipeState: State<GenerateRecipeState> = _generateRecipeState

    // map for holding the current generate recipe state
    var staticIngredients = mutableStateOf(mapOf<String, List<CheckableItem>>())

    // map for holding custom ingredients added by user
    var customIngredients = mutableStateOf(mapOf<String, List<CheckableItem>>())

    init {
        getStaticIngredients()
    }

    /**
     * Add a custom ingredient
     * @param name The name of the custom ingredient
     * @param category The category to add to, or null for standalone
     */
    fun addCustomIngredient(name: String, category: String?) {
        if (name.isBlank()) return

        val newCustomIngredients = customIngredients.value.toMutableMap()
        val targetCategory = category ?: "Custom"

        val existingList = newCustomIngredients[targetCategory]?.toMutableList() ?: mutableListOf()
        existingList.add(CheckableItem(title = name))
        newCustomIngredients[targetCategory] = existingList

        customIngredients.value = newCustomIngredients
    }

    /**
     * Get all ingredients (static + custom) combined
     */
    fun getAllIngredients(): Map<String, List<CheckableItem>> {
        return staticIngredients.value.toMutableMap().apply {
            putAll(customIngredients.value)
        }
    }

    /**
     * Function to initiate the recipe generation process.
     * Launches a coroutine to handle asynchronous operations and updates the generate recipe state accordingly.
     */
    fun generateRecipe() =
        viewModelScope.launch {
            val prompt = preparePromptFromIngredients()
            Log.i(TAG, "generateRecipe: $${prompt}")

            // Call the use case to generate a recipe based on the current prompt value.
            val generatedRecipeFlow = useCase.generateRecipe(prompt)

            // Collect the latest value emitted by the generatedRecipeFlow coroutine flow.
            generatedRecipeFlow.collectLatest { resource ->
                // Update the generate recipe state based on the type of resource emitted.
                when (resource) {
                    is Resource.Error ->
                        updateGenerateRecipeState(
                            _generateRecipeState.value.copy(
                                isLoading = false,
                                isErrorMessage = resource.message
                                    ?: "Oops! Something went wrong😵‍💫",
                            ),
                        )

                    is Resource.Loading ->
                        updateGenerateRecipeState(
                            _generateRecipeState.value.copy(
                                isLoading = true,
                            ),
                        )

                    is Resource.Success ->
                        updateGenerateRecipeState(
                            _generateRecipeState.value.copy(
                                isLoading = false,
                                generatedRecipe = resource.data ?: Recipe.INVALID_RECIPE,
                            ),
                        )
                }
            }
        }


    /**
     * Private function to update the generate recipe state.
     *
     * @param generateRecipeState The new generate recipe state to set.
     */
    private fun updateGenerateRecipeState(generateRecipeState: GenerateRecipeState) {
        _generateRecipeState.value = generateRecipeState
    }


    // Fetch static ingredients and update the state
    private fun getStaticIngredients() {
        staticIngredients.value = useCase.getStaticIngredient()
    }

    // Prepare a prompt string from the selected ingredients
    private fun preparePromptFromIngredients(): String {
        val promptStr: StringBuilder = StringBuilder()
        val allIngredients = getAllIngredients()
        for ((key, value) in allIngredients) {
            val selectedItems = value.filter { it.isSelected }
            if (selectedItems.isNotEmpty()) {
                promptStr.append(" $key : ${selectedItems.joinToString { it.title }}")
                promptStr.append(",")
            }
        }
        return promptStr.toString()
    }

    // Clear the selection status of all ingredients (static and custom)
    fun clearAllSelection() {
        // Reset static ingredients
        val resetStaticIngredients = staticIngredients.value.toMutableMap()
        resetStaticIngredients.entries.forEach { (_, value) ->
            value.forEach {
                it.isSelected = false
            }
        }
        staticIngredients.value = mutableMapOf()
        staticIngredients.value = resetStaticIngredients

        // Reset custom ingredients
        val resetCustomIngredients = customIngredients.value.toMutableMap()
        resetCustomIngredients.entries.forEach { (_, value) ->
            value.forEach {
                it.isSelected = false
            }
        }
        customIngredients.value = mutableMapOf()
        customIngredients.value = resetCustomIngredients
    }

    // Reset the recipe detail state to an invalid state
    fun resetRecipeDetail() {
        _generateRecipeState.value = GenerateRecipeState.INVALID_GENERATE_RECIPE_STATE
    }

    companion object {
        private const val TAG = "GenerateRecipeViewModel"
    }
}
