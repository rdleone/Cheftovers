package com.example.cheftovers.ui.ingredient.viewmodels

/**
 * Ingredient screen state for ViewModel
 *
 * @property currentIngredientList Live list of ingredients entered by user
 */
data class IngredientUIState(
    val currentIngredientList: MutableList<String> = arrayListOf()
)