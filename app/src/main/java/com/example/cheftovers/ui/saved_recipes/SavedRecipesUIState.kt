package com.example.cheftovers.ui.saved_recipes

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.cheftovers.data.recipe.Recipe
import com.example.cheftovers.data.recipe.recipeSample

/**
 * Recipe state for Saved Recipes ViewModel
 *
 * @property savedRecipes Live list of recipes saved by user
 * @property recipe Current recipe viewed by user
 */
data class SavedRecipesUIState(
    // TODO: Determine if this list can be immutable
    val savedRecipes: SnapshotStateList<Recipe> = mutableStateListOf(),
    val recipe: Recipe = recipeSample()
)