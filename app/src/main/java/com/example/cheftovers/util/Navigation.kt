package com.example.cheftovers.util

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cheftovers.data.recipeSample
import com.example.cheftovers.ui.home.HomeScreen
import com.example.cheftovers.ui.home.HomeViewModel
import com.example.cheftovers.ui.ingredient.IngredientScreen
import com.example.cheftovers.ui.ingredient.IngredientViewModel
import com.example.cheftovers.ui.recipe_details.RecipeDetailsScreen
import com.example.cheftovers.ui.recipe_details.RecipeDetailsViewModel
import com.example.cheftovers.ui.recipe_results.RecipeResultsViewModel
import com.example.cheftovers.ui.recipes.screen.SavedRecipesScreen
import com.example.cheftovers.ui.recipes.search.RecipeResultsScreen
import com.example.cheftovers.ui.saved_recipes.SavedRecipesViewModel

/**
 * Functionality for navigation between each screen using host navController
 */
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.HomeScreen) {
        composable(route = Routes.HomeScreen) {
            HomeScreen(
                viewModel = viewModel<HomeViewModel>(),
                onNavigate = { navController.navigate(it.route) }
            )
        }
        composable(Routes.IngredientScreen) {
            IngredientScreen(
                viewModel = viewModel<IngredientViewModel>(),
                onNavigate = { navController.navigate(it.route) }
            )
        }
        composable(route = Routes.RecipeResultsScreen) {
            RecipeResultsScreen(
                viewModel = viewModel<RecipeResultsViewModel>(),
                onNavigate = { navController.navigate(it.route) }
            )
        }
        composable(route = Routes.RecipeDetailsScreen) {
            RecipeDetailsScreen(
                viewModel = viewModel<RecipeDetailsViewModel>(),
                onNavigate = { navController.navigate(it.route) },
                recipe = recipeSample()
            )
        }
        composable(route = Routes.SavedRecipesScreen) {
            SavedRecipesScreen(
                viewModel = viewModel<SavedRecipesViewModel>(),
                onNavigate = { navController.navigate(it.route) }
            )
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
                    indicatorColor = MaterialTheme.colorScheme.onSecondary
                ),
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