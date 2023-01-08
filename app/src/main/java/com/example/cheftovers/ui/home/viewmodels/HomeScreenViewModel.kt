package com.example.cheftovers.ui.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.cheftovers.util.Routes

/**
 * Handles logic for any buttons on the home screen, specifically the ingredient search button
 *
 * @param navController navigation controller that allows movement across screens
 */
class HomeScreenViewModel(val navController: NavController) : ViewModel() {

    fun onIngredientSearchClick() {
        navController.navigate(Routes.IngredientScreen)
    }
}