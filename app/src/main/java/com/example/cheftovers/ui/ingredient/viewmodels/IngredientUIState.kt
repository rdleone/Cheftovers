package com.example.cheftovers.ui.ingredient.viewmodels

/**
 * Data class for the ingredient view model that holds variables and adds getters automatically
 *
 * @param currentIngredientList navigation controller that allows movement across screens
 */
data class IngredientUIState(val currentIngredientList: MutableList<String> = arrayListOf())