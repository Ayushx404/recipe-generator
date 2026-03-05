package com.ayush.recipegenerator.presentation.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ayush.recipegenerator.R
import com.ayush.recipegenerator.presentation.common.Loader
import com.ayush.recipegenerator.presentation.common.NoRecipeFound
import com.ayush.recipegenerator.presentation.home.composables.GetStartedBox
import com.ayush.recipegenerator.presentation.home.composables.HeroImageCard
import com.ayush.recipegenerator.presentation.home.composables.HomeHeader
import com.ayush.recipegenerator.presentation.recipes.composables.RecipesList


/**
 * Composable function for displaying the Home screen.
 * It uses HomeScaffold as the main layout container.
 *
 * @param navigateToGenerateRecipeScreen Callback to navigate to the Generate Recipe screen.
 */
@Composable
fun HomeScreen(
    navigateToGenerateRecipeScreen: () -> Unit,
    navigateToRecipeListScreen: (showBackIcon: Boolean) -> Unit,
    navigateToRecipeDetailScreen: (id: Int) -> Unit,
    onLogoutClick: () -> Unit = {}
) {
    // Render the HomeScaffold passing the navigateToGenerateRecipeScreen callback
    HomeScaffold(
        navigateToGenerateRecipeScreen = navigateToGenerateRecipeScreen,
        navigateToRecipeListScreen = navigateToRecipeListScreen,
        navigateToRecipeDetailScreen = navigateToRecipeDetailScreen,
        onLogoutClick = onLogoutClick
    )
}

/**
 * Private composable function for the Home screen scaffold.
 * It uses Scaffold to provide basic material design structure.
 *
 * @param navigateToGenerateRecipeScreen Callback to navigate to the Generate Recipe screen.
 */
@Composable
private fun HomeScaffold(
    navigateToGenerateRecipeScreen: () -> Unit,
    navigateToRecipeListScreen: (showBackIcon: Boolean) -> Unit,
    navigateToRecipeDetailScreen: (id: Int) -> Unit,
    onLogoutClick: () -> Unit
) {
    val viewModel: HomeViewModel = hiltViewModel()

    // Observe the recipe detail state
    val homeState by remember { viewModel.homeState }

    val scrollState = rememberLazyListState()

    val shouldHideBottomBar by remember(scrollState) {
        derivedStateOf { scrollState.lastScrolledBackward || scrollState.firstVisibleItemIndex == 0 }
    }
        // Scaffold provides a basic material design structure with padding
    Scaffold(

    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            // Display the header of the Home screen
            HomeHeader(onLogoutClick = onLogoutClick)

            // Hero image card
            HeroImageCard(onGetStartedButtonClick = navigateToGenerateRecipeScreen)

            // Button to get started and navigate to Generate Recipe screen
            GetStartedBox(onGetStartedButtonClick = navigateToGenerateRecipeScreen)

            when {
                homeState.showNoRecipeGenerated -> {
                    // Component to handle scenario when no recipes are found
                    NoRecipeFound(
                        showAddExpenseButton = true,
                        onGenerateRecipeButtonClick = navigateToGenerateRecipeScreen
                    )
                }

                homeState.isLoading -> {
                    Loader()
                }

                else -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.history),
                                style = MaterialTheme.typography.titleLarge
                            )


                            Box(
                                modifier = Modifier
                                    .shadow(shape = RoundedCornerShape(20.dp), elevation = 1.dp)
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                    .clickable {
                                        navigateToRecipeListScreen(true)
                                    },
                            ) {
                                Text(
                                    modifier =
                                    Modifier
                                        .padding(horizontal = 10.dp, vertical = 4.dp),
                                    text = stringResource(R.string.show_all),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontSize = 12.sp,
                                )
                            }
                        }

                        RecipesList(
                            recipes = homeState.generatedRecipes,
                            showNoOfRecipes = 17,
                            scrollState = scrollState,
                            onRecipeClicked = {
                                navigateToRecipeDetailScreen(it.id)
                            },
                            onRecipeDelete = viewModel::deleteRecipe)
                    }
                }
            }
        }
    }
}

/**
 * Preview function for HomeScaffold composable.
 * It shows the system UI and previews on a Pixel 5 device.
 */
@Preview(showSystemUi = true, device = Devices.PIXEL_5, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeScreenPreview() {
    HomeScaffold(navigateToGenerateRecipeScreen = {}, navigateToRecipeListScreen = {}, navigateToRecipeDetailScreen = {}, onLogoutClick = {})
}
