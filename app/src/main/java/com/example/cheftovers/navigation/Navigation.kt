package com.example.cheftovers.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cheftovers.data.recipeSample
import com.example.cheftovers.ui.home.HomeScreen
import com.example.cheftovers.ui.home.viewmodels.HomeScreenViewModel
import com.example.cheftovers.ui.ingredient.screen.IngredientScreen
import com.example.cheftovers.ui.ingredient.viewmodels.IngredientViewModel
import com.example.cheftovers.ui.recipes.screen.RecipeDetails
import com.example.cheftovers.ui.recipes.screen.SavedRecipesScreen
import com.example.cheftovers.ui.recipes.search.RecipeResultsScreen
import com.example.cheftovers.ui.recipes.viewmodels.RecipeViewModel

/**
 * Functionality for navigation between each screen, uses navController
 *
 * Note: Routes are located in navigation/ScreenNavigationInfo.kt
 */
@Composable
fun Navigation(navController: NavHostController) {
    val recipeViewModel = RecipeViewModel(navController)
    val recipeUIState by recipeViewModel.recipeUIStateStream.collectAsState()
    NavHost(navController = navController, startDestination = ScreenRoute.HomeScreen.route) {
        composable(route = ScreenRoute.HomeScreen.route) {
            HomeScreen(homeScreenViewModel = HomeScreenViewModel(navController))
        }
        composable(route = ScreenRoute.IngredientScreen.route) {
            IngredientScreen(ingredientViewModel = IngredientViewModel(navController = navController)) }
        composable(route = ScreenRoute.RecipeResultsScreen.route) {
            RecipeResultsScreen(recipeViewModel = recipeViewModel)
        }
        composable(route = ScreenRoute.RecipeDetailsScreen.route) {
            // So the recipe details screen has a Recipe param, which makes sense.
            // But it needs to be passed a recipe here for the navigation to work.
            // I have the sample here for now, but idk what we would replace this
            // with in the future. Maybe we need to get the ID of the recipe
            // selected to pass into here?
            RecipeDetails(recipe = recipeSample(), savedRecipeViewModel = recipeViewModel)
        }
        composable(route = ScreenRoute.SavedRecipesScreen.route) {
            SavedRecipesScreen(recipeViewModel = recipeViewModel,
                recipeUIState = recipeUIState)
        }
    }
}

/**
 * Modified NavigationBar that uses the BottomNavItem data class
 *
 * I totally didn't use a YouTube video such as
 * https://www.youtube.com/watch?v=4xyRnIntwTo
 * to figure out how to do this...
 *
 * @param modifier      Default Modifier
 * @param navController Host NavController
 * @param items         All BottomNavItems to display in the bar
 * @param onItemClick   Navigation function for each item
 */
@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    items: List<BottomNavItem>,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.secondary,
        tonalElevation = 5.dp
    ) {
        items.forEach { item ->
            // The route needs to be hoisted to properly update the UI
            val selected = item.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.onSecondary),
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.name,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            )
        }
    }
}