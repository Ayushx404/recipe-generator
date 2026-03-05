package com.ayush.recipegenerator.presentation.saved_recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ayush.recipegenerator.R
import com.ayush.recipegenerator.common.util.recipes
import com.ayush.recipegenerator.presentation.common.Loader
import com.ayush.recipegenerator.presentation.common.NoRecipeFound
import com.ayush.recipegenerator.presentation.recipes.composables.RecipesList

@Composable
fun SavedRecipeScreen(
    modifier: Modifier = Modifier,
    viewModel: SavedRecipeViewModel = hiltViewModel(),
    onBackIconClick: () -> Unit,
    navToGenerateRecipeScreen: () -> Unit,
) {
    val savedRecipesState by remember { viewModel.savedRecipeState }

    LaunchedEffect(key1 = Unit) {
        viewModel.getSavedRecipes()
    }

    SavedRecipeScaffold(
        modifier = modifier,
        savedRecipesState = savedRecipesState,
        onBackIconClick = onBackIconClick,
        navToGenerateRecipeScreen = navToGenerateRecipeScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedRecipeScaffold(
    modifier: Modifier = Modifier,
    savedRecipesState: SavedRecipeState,
    onBackIconClick: () -> Unit,
    navToGenerateRecipeScreen: () -> Unit,
) {
    val lazyListState = rememberLazyListState()

    val viewModel: SavedRecipeViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.saved_recipes),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
            )
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier.padding(paddingValue)
        ) {
            when {
                savedRecipesState.showNoRecipeGenerated -> {
                    // Component to handle scenario when no recipes are found
                    NoRecipeFound(
                        showAddExpenseButton = true,
                        messageId = R.string.no_saved_recipe_found,
                        onGenerateRecipeButtonClick = { }
                    )
                }

                savedRecipesState.isLoading -> {
                   Loader()
                }

                else -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        RecipesList(
                            recipes = savedRecipesState.recipes,
                            scrollState = lazyListState,
                            onRecipeClicked = {},
                            onRecipeDelete = viewModel::deleteRecipe)
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
private fun SavedRecipesScreenPreview() {
    val recipeState =
        SavedRecipeState(recipes = recipes(), isLoading = false, showNoRecipeGenerated = false)
    SavedRecipeScaffold(savedRecipesState = recipeState, onBackIconClick = {}, navToGenerateRecipeScreen = {})
}