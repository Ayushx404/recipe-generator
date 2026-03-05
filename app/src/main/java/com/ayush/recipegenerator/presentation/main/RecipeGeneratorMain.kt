package com.ayush.recipegenerator.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ayush.recipegenerator.R
import com.ayush.recipegenerator.common.navigation.AppNavHost
import com.ayush.recipegenerator.common.navigation.NavItem
import com.ayush.recipegenerator.common.navigation.Destination
import com.ayush.recipegenerator.common.navigation.Route
import com.ayush.recipegenerator.presentation.home.composables.BottomNavBar
import com.ayush.recipegenerator.presentation.login.LoginViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth

/**
 * Composable function for the main entry point of the Recipe Generator app.
 * It sets up the main UI structure using Jetpack Compose components.
 *
 * @param modifier Modifier for adjusting the layout and appearance of the main container.
 */
@Composable
fun RecipeGeneratorMain(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        val navHostController: NavHostController = rememberNavController()
        var showBottomBar by remember {
            mutableStateOf(true)
        }
        
        val startDestination = if (loginViewModel.isUserLoggedIn()) {
            Destination.HomeScreen.route
        } else {
            Destination.LoginScreen.route
        }

        navHostController.addOnDestinationChangedListener { controller, destination, _ ->
            showBottomBar =
                (destination.route == Route.HOME_SCREEN || 
                 destination.route?.contains(Route.RECIPE_LIST) == true || 
                 destination.route == Route.SAVED_RECIPE_LIST) && 
                 destination.route != Route.LOGIN_SCREEN
        }

        // Surface provides a background color for the entire screen
        Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
            AnimatedVisibility(visible = showBottomBar) {
                BottomNavBar(navItems = getNavItems(), navController = navHostController)
            }
        }) { innerPadding ->
            // Scaffold sets up the basic material design structure
            // AppNavHost manages navigation between different screens
            AppNavHost(modifier.padding(innerPadding), navHostController, startDestination)
        }
    }
}

/**
 * Preview function for RecipeGeneratorMain composable.
 * Shows how RecipeGeneratorMain appears in different devices and system UI states.
 */
@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
private fun RecipeGeneratorMainPreview() {
    // Render the RecipeGeneratorMain composable in preview mode
    RecipeGeneratorMain()
}


@Composable
fun getNavItems() = listOf(
    NavItem(
        title = "Home",
        icon = painterResource(id = R.drawable.ic_home_vector),
        screenRoute = Route.HOME_SCREEN,
        selected = true
    ),
    NavItem(
        title = "Recipes",
        icon = painterResource(id = R.drawable.ic_recipes_vector),
        screenRoute = Route.RECIPE_LIST,
        selected = true
    ),

    NavItem(
        title = "Saved",
        icon = painterResource(id = R.drawable.ic_filled_saved_vector),
        screenRoute = Route.SAVED_RECIPE_LIST,
        selected = true
    ),
)