package com.example.cheftovers.navigation

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Defines navigation route for each screen
 */
sealed class ScreenRoute(val route: String) {
    object HomeScreen: ScreenRoute(route = "home_screen")
    object IngredientScreen: ScreenRoute(route = "ingredient_screen")
    object RecipeResultsScreen: ScreenRoute(route = "recipe_results_screen")
    object RecipeDetailsScreen: ScreenRoute(route = "recipe_details_screen")
    object SavedRecipesScreen: ScreenRoute(route = "saved_recipes_screen")
}

/**
 * Data for each badge in the navigation bar
 * @property name   Page name
 * @property route  Page navigation route
 * @property icon   Icon representation of page
 */
data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector,
)