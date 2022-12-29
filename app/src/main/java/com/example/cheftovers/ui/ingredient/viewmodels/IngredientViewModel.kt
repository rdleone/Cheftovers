package com.example.cheftovers.ui.ingredient.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.cheftovers.navigation.ScreenRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


/**
 * Handles logic for any buttons on the ingredient search screen, specifically the search button
 * This will update the variables from the IngredientUIState and be able to use it across the app
 *
 * @param navController navigation controller that allows movement across screens
 */
class IngredientViewModel(val navController: NavController) : ViewModel() {

    private val uiState = MutableStateFlow(IngredientUIState())
    val ingredientUIStateStream: StateFlow<IngredientUIState>
        get() = uiState

    fun onSearchClick(list: MutableList<String>) {
        uiState.update { it.copy(currentIngredientList = list) }
        Log.i("onSearchClick", "current list size: ${uiState.value.currentIngredientList.size}")
        navController.navigate(ScreenRoute.RecipeResultsScreen.route)
    }
}