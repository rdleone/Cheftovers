package com.example.cheftovers.ui.recipes.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.cheftovers.data.Recipe
import com.example.cheftovers.util.Routes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * Handles logic for any buttons on the saved recipe screen, specifically the favorite star icon
 *
 * @param navController navigation controller that allows movement across screens
 */
class RecipeViewModel(val navController: NavController) : ViewModel() {

    private val uiState = MutableStateFlow(RecipeUIState())
    val recipeUIStateStream: StateFlow<RecipeUIState>
        get() = uiState

    init {
        uiState.value = recipeUIStateStream.value.copy()
    }

    fun onFavoriteClick(recipe: Recipe) {

        val savedRecipesMutableList = uiState.value.savedRecipes.toMutableList()
        Log.i(
            "onFavoriteClick",
            "size of recipes saved before add/remove: ${uiState.value.savedRecipes.size}"
        )
        if (savedRecipesMutableList.contains(recipe)) {
            savedRecipesMutableList.remove(recipe)
            uiState.update { it.copy(savedRecipes = savedRecipesMutableList) }
        } else {
            savedRecipesMutableList.add(recipe)
            uiState.update { it.copy(savedRecipes = savedRecipesMutableList) }
        }
        uiState.value = recipeUIStateStream.value.copy()
        Log.i("onFavoriteClick", "size of recipes saved: ${uiState.value.savedRecipes.size}")
    }

    fun onBackPressed() {
//        if(navController.currentBackStackEntry?.id == ScreenRoute.RecipeResultsScreen.route) {
//            navController.navigate(ScreenRoute.RecipeResultsScreen.route)
//        } else {
//            navController.navigate(ScreenRoute.SavedRecipesScreen.route)
//        }
        navController.navigate(Routes.RecipeResultsScreen)

    }

    fun onCardClicked() {
        navController.navigate(Routes.RecipeDetailsScreen)
    }

}