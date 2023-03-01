package com.example.cheftovers.ui.recipe_results

import com.example.cheftovers.data.Recipe
import com.example.cheftovers.data.recipeSample

/**
 * Recipe state for Recipe Results and Saved Recipes ViewMoel
 *
 * @property recipes List of recipes displayed based on ingredient list
 * @property currRecipe Current recipe viewed by user
 */
data class RecipeResultsUIState(
    val recipes: List<Recipe> = List(10) { recipeSample() },
//    val currRecipe: Recipe = recipeSample()
)
