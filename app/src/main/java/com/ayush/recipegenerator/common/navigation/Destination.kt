package com.ayush.recipegenerator.common.navigation

/**
 * Sealed class representing different destinations in the application.
 *
 * @param route Unique route identifier for each destination.
 */
sealed class Destination(
    val route: String,
) {
    /**
     * Represents the home screen destination.
     */
    data object HomeScreen : Destination(Route.HOME_SCREEN)

    /**
     * Represents the generate recipe screen destination.
     */
    data object GenerateRecipeScreen : Destination(Route.GENERATE_RECIPE)

    /**
     * Represents the recipes screen destination.
     */
    data object RecipeScreen : Destination(Route.RECIPES)

    /**
     * Represents the recipe detail screen destination.
     */
    data object RecipeDetailScreen : Destination(Route.RECIPE_DETAIL)

    /**
     * Represents the recipe list screen destination.
     */
    data object RecipeListScreen : Destination(Route.RECIPE_LIST)

    /**
     * Represents the save recipes list screen destination.
     */
    data object SaveRecipeListScreen : Destination(Route.SAVED_RECIPE_LIST)

    /**
     * Represents the login screen destination.
     */
    data object LoginScreen : Destination(Route.LOGIN_SCREEN)
}
