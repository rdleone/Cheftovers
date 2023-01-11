package com.example.cheftovers.ui.saved_recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cheftovers.ui.recipe_results.RecipeResultsEvent
import com.example.cheftovers.ui.recipe_results.RecipeResultsUIState
import com.example.cheftovers.util.Routes
import com.example.cheftovers.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Handles logic for the Saved Recipes screen, namely launching
 * a recipe's details screen from its card.
 */
class SavedRecipesViewModel : ViewModel() {

    private val _savedRecipesState = MutableStateFlow(SavedRecipesUIState())
    val savedRecipesState: StateFlow<SavedRecipesUIState>
        get() = _savedRecipesState

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    /**
     * Handles each type of RecipeResultsEvent
     * (recall that Saved Recipes shares this event type)
     */
    fun onEvent(event: RecipeResultsEvent) {
        when (event) {
            is RecipeResultsEvent.OnCardClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.RecipeDetailsScreen))
            }
        }
    }

    /**
     * Used by ViewModel to launch UI events in a coroutine scope.
     */
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}