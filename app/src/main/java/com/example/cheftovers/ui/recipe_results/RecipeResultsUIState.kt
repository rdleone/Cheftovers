package com.example.cheftovers.ui.recipe_results

import com.example.cheftovers.data.recipe.Recipe

/**
 * Recipe state for Recipe Results and Saved Recipes ViewModel
 *
 * @property recipes List of recipes displayed based on ingredient list
 */
data class RecipeResultsUIState(
    val recipes: List<Recipe> = listOf(),
)
