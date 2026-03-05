package com.ayush.recipegenerator.presentation.recipes

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
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
fun RecipeListScreen(
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
    viewModel: RecipeListViewModel = hiltViewModel(),
    onBackIconClick: () -> Unit,
    navToRecipeDetail: (Int) -> Unit,
    navToGenerateRecipeScreen: () -> Unit,
    ) {
    val recipeListState by remember { viewModel.recipeListState }
    LaunchedEffect(key1 = Unit) {
        viewModel.getGenerateRecipes()
    }
    RecipeListScaffold(
        modifier = modifier,
        showBackButton = showBackButton,
        recipeListState = recipeListState,
        onBackIconClick = onBackIconClick,
        navToRecipeDetail = navToRecipeDetail,
        navToGenerateRecipeScreen = navToGenerateRecipeScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScaffold(
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
    recipeListState: RecipeListState,
    onBackIconClick: () -> Unit,
    navToRecipeDetail: (Int) -> Unit,
    navToGenerateRecipeScreen: () -> Unit,
    ) {
    val lazyListState = rememberLazyListState()
    val viewModel:RecipeListViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.all_recipes),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    Log.i("RecipeListScreen", "RecipeListScaffold: $showBackButton")
                    if (showBackButton) {
                        IconButton(onClick = onBackIconClick) {
                            Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
            )
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier.padding(paddingValue)
        ) {
            when {
                recipeListState.showNoRecipeGenerated -> {
                    // Component to handle scenario when no recipes are found
                    NoRecipeFound(
                        showAddExpenseButton = true,
                        onGenerateRecipeButtonClick = navToGenerateRecipeScreen
                    )
                }

                recipeListState.isLoading -> {
                    Loader()
                }

                else -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        RecipesList(
                            recipes = recipeListState.recipes,
                            scrollState = lazyListState,
                            onRecipeClicked = {
                                navToRecipeDetail(it.id)
                            },
                            onRecipeDelete = viewModel::deleteRecipe)
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
private fun RecipeListScreenPreview() {
    val recipeState =
        RecipeListState(recipes = recipes(), isLoading = false, showNoRecipeGenerated = false)
    RecipeListScaffold(recipeListState = recipeState, onBackIconClick = {}, navToRecipeDetail = {}, navToGenerateRecipeScreen = {})
}