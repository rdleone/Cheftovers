package com.example.cheftovers.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cheftovers.util.Routes
import com.example.cheftovers.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Handles logic for any buttons on the home screen,
 * specifically the ingredient search button.
 */
class HomeViewModel() : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    /**
     * Handles each type of HomeEvent.
     */
    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.OnRecipeSearch -> {
                sendUiEvent(UiEvent.Navigate(Routes.IngredientScreen))
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