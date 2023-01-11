package com.example.cheftovers.ui.ingredient

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

/**
 * Ingredient screen state for ViewModel
 *
 * @property currentIngredientList Live list of ingredients entered by user
 */
data class IngredientUIState(
    val currentIngredientList: SnapshotStateList<String> = mutableStateListOf()
)