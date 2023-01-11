package com.example.cheftovers.ui.recipe_results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cheftovers.util.Routes
import com.example.cheftovers.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Handles logic for the Recipe Results screen, namely launching
 * a recipe's details screen from its card.
 */
class RecipeResultsViewModel() : ViewModel() {

    private val _recipeResultsState = MutableStateFlow(RecipeResultsUIState())
    val recipeResultsState: StateFlow<RecipeResultsUIState>
        get() = _recipeResultsState

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    /**
     * Handles each type of RecipeResultsEvent
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