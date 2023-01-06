package com.example.cheftovers.ui.recipes.viewmodels

import com.example.cheftovers.data.Recipe
import com.example.cheftovers.data.recipeSample

/**
 * Recipe state for Recipe Results and Saved Recipes ViewMoel
 *
 * @property savedRecipes Live list of recipes saved by user
 * @property recipe Current recipe viewed by user
 */
data class RecipeUIState(
    val savedRecipes: MutableList<Recipe> = arrayListOf(),
    val recipe: Recipe = recipeSample()
)