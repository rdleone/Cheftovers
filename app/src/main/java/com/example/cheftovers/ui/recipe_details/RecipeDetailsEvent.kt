package com.example.cheftovers.ui.recipe_details

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.cheftovers.data.recipe.Recipe

/**
 * User-initiated events on the Recipe Details screen
 */
sealed class RecipeDetailsEvent {
    object OnBack : RecipeDetailsEvent()
    data class OnFavorite(
        val recipe: Recipe,
        val savedRecipes: SnapshotStateList<Recipe>
    ) : RecipeDetailsEvent()
}
