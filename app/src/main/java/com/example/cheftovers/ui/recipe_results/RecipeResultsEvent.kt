package com.example.cheftovers.ui.recipe_results

import com.example.cheftovers.data.recipe.Recipe

/**
 * User-initiated events on the Recipe Results AND Saved Recipes screen
 * since they have similar functionality.
 */
sealed class RecipeResultsEvent {
    /**
     * Takes user to the recipe represented by the clicked card.
     * @property recipe The recipe represented by the card
     */
    data class OnCardClick(
        val recipe: Recipe
    ) : RecipeResultsEvent()
}
