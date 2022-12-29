package com.example.cheftovers.ui.recipes.viewmodels

import com.example.cheftovers.data.Recipe
import com.example.cheftovers.data.recipeSample

data class RecipeUIState(
    val savedRecipes: List<Recipe> = emptyList(),
    val recipe: Recipe = recipeSample()
)