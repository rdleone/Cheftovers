package com.example.cheftovers.ui.recipe_details

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.cheftovers.data.Recipe
import com.example.cheftovers.data.recipeSample

/**
 * Recipe state for Recipe Details ViewModel
 *
 * @property savedRecipes Live list of recipes saved by user
 * @property recipe Current recipe viewed by user
 */
data class RecipeDetailsUIState(
    val savedRecipes: SnapshotStateList<Recipe> = mutableStateListOf(),
    val recipe: Recipe = recipeSample()
)