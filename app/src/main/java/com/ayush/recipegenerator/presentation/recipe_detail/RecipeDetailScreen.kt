package com.ayush.recipegenerator.presentation.recipe_detail

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ayush.recipegenerator.presentation.common.Loader
import com.ayush.recipegenerator.presentation.recipe_detail.composables.RecipeDetailButtons
import com.ayush.recipegenerator.presentation.recipe_detail.composables.RecipeIngredient
import com.ayush.recipegenerator.presentation.recipe_detail.composables.RecipeInstruction
import com.ayush.recipegenerator.presentation.recipe_detail.composables.RecipeTip
import com.ayush.recipegenerator.presentation.recipe_detail.composables.RecipeVariation


/**
 * A Composable function to display the detail screen of a recipe.
 *
 * @param modifier A [Modifier] for styling and layout purposes.
 * @param recipeId The ID of the recipe to be displayed.
 * @param onPop A lambda function to be called when the back button is pressed.
 */
@Composable
fun RecipeDetailScreen(
    modifier: Modifier = Modifier,
    recipeId: Int,
    onPop: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    // Call the scaffold that manages the layout of the screen
    RecipeDetailScaffold(
        modifier = modifier,
        recipeId = recipeId,
        onPop = onPop,
        navigateToHomeScreen = navigateToHomeScreen
    )
}

/**
 * A Composable function to create a scaffold layout for the recipe detail screen.
 *
 * @param modifier A [Modifier] for styling and layout purposes.
 * @param recipeId The ID of the recipe to be displayed.
 * @param onPop A lambda function to be called when the back button is pressed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScaffold(
    modifier: Modifier = Modifier,
    recipeId: Int,
    onPop: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    // Obtain the RecipeDetailViewModel instance using Hilt
    val recipeDetailViewModel: RecipeDetailViewModel = hiltViewModel()

    // Observe the recipe detail state
    val recipeDetailState by remember { recipeDetailViewModel.recipeDetailState }

    // Load recipe details when the composable is launched
    LaunchedEffect(key1 = Unit) {
        if (recipeId != -1) {
            recipeDetailViewModel.getRecipe(recipeId)
        } else {
            recipeDetailViewModel.resetRecipeState()
        }
    }

    // Handle back button press
    BackHandler {
        recipeDetailViewModel.resetRecipeState()
        onPop()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = recipeDetailState.recipe.apiRecipe.name ?: "")
            }, navigationIcon = {
                IconButton(onClick = {
                    recipeDetailViewModel.resetRecipeState()
                    onPop()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            })
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when {
                recipeDetailState.isLoading -> {
                    // Show loading indicator while loading
                    Loader()
                }

                recipeDetailState.error.isNotEmpty() -> {
                    // Show error message if an error occurs
                    Toast.makeText(
                        LocalContext.current,
                        recipeDetailState.error,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                else -> {

                    var isSaved by remember {
                        mutableStateOf(false)
                    }

                    if (recipeDetailState.isRecipeSaved) {
                        isSaved = true
//                        Toast.makeText(LocalContext.current, "Recipe Saved!", Toast.LENGTH_SHORT)
//                            .show()
                    }

                    Box {
                        // Display recipe details in a LazyColumn
                        val recipe = recipeDetailState.recipe.apiRecipe
                        LazyColumn() {
                            item {
                                RecipeIngredient(ingredients = recipe.apiIngredients)
                            }
                            item {
                                if (recipe.apiInstructions.isNotEmpty()) {
                                    RecipeInstruction(instructions = recipe.apiInstructions)
                                }
                            }
                            item {
                                if (recipe.tips.isNotEmpty()) {
                                    RecipeTip(tips = recipe.tips)
                                }
                            }
                            item {
                                if (recipe.apiVariations.isNotEmpty()) {
                                    RecipeVariation(variations = recipe.apiVariations)
                                }
                            }
                            item {
                                Spacer(modifier = Modifier.height(70.dp))
                            }
                        }

                        RecipeDetailButtons(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .background(MaterialTheme.colorScheme.onPrimaryContainer)
                                .padding(16.dp), recipeDetailViewModel,
                            recipeId,
                            isSaved,
                            navigateToHomeScreen
                        )
                    }
                }
            }
        }
    }
}


/**
 * A Composable function for previewing the [RecipeDetailScreen] composable.
 *
 * This function is used only in the Android Studio Preview and not in production.
 */
@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
private fun RecipeDetailScreenPreview() {
    // Display the RecipeDetailScaffold composable with sample data
    RecipeDetailScaffold(recipeId = 1, onPop = {}, navigateToHomeScreen = {})
}

