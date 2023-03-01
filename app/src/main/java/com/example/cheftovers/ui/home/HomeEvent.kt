package com.example.cheftovers.ui.home

/**
 * User-initiated events on the Home screen.
 */
sealed class HomeEvent {
    /**
     * Navigates user to Ingredient List screen to begin
     * using the Recipe Search.
     */
    object OnRecipeSearch: HomeEvent()
}
