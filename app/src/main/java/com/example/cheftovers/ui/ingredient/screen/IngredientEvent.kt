package com.example.cheftovers.ui.ingredient.screen

import androidx.compose.runtime.snapshots.SnapshotStateList

/**
 * User-initiated events on the Ingredient screen
 */
sealed class IngredientEvent {
    data class AddIngredient(
        val ingredient: String,
        val ingrList: SnapshotStateList<String>
        ): IngredientEvent()
    data class RemoveIngredient(
        val ingredient: String,
        val ingrList: SnapshotStateList<String>
        ): IngredientEvent()
    data class onFindRecipes(val ingrList: SnapshotStateList<String>): IngredientEvent()
}
