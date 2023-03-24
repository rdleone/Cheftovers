package com.example.cheftovers.ui.ingredient

import androidx.compose.runtime.snapshots.SnapshotStateList

/**
 * User-initiated events on the Ingredient List screen
 */
sealed class IngredientEvent {
    /**
     * Adds the ingredient string typed by the user
     * into the current ingredient list.
     *
     * @property ingredient Ingredient string typed by the user
     * @property ingrList Current ingredient list
     */
    data class AddIngredient(
        val ingredient: String,
        val ingrList: SnapshotStateList<String>,
    ) : IngredientEvent()

    /**
     * Removes the ingredient string typed by the user
     * from the current ingredient list.
     *
     * @property ingredient Ingredient string typed by the user
     * @property ingrList Current ingredient list
     */
    data class RemoveIngredient(
        val ingredient: String,
        val ingrList: SnapshotStateList<String>
    ) : IngredientEvent()

    /**
     * Handles edge cases and invalid inputs provided
     * by the user from the ingredient text field.
     *
     * @property message Message to display within the Toast
     */
    data class OnEntryError(
        val message: String
    ) : IngredientEvent()

    /**
     * Initiates the recipe search using the current
     * list of ingredients.
     *
     * @property ingrList Current ingredient list
     */
    data class OnFindRecipes(
        val ingrList: SnapshotStateList<String>
    ) : IngredientEvent()
}
