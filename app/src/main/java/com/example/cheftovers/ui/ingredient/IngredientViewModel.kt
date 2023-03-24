package com.example.cheftovers.ui.ingredient

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cheftovers.util.Routes
import com.example.cheftovers.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Handles logic for any buttons on the ingredient search screen, specifically the search button
 * This will update the variables from the IngredientUIState and be able to use it across the app.
 */
class IngredientViewModel : ViewModel() {

    private val _ingrState = MutableStateFlow(IngredientUIState())
    val ingrState: StateFlow<IngredientUIState>
        get() = _ingrState

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    /**
     * Handles each type of IngredientEvent.
     */
    fun onEvent(event: IngredientEvent) {
        when (event) {
            is IngredientEvent.AddIngredient -> {
                event.ingrList.add(event.ingredient)
                _ingrState.update { it.copy(currentIngredientList = event.ingrList) }
                Log.i(
                    "ingrEvent", "Ingredient added: " +
                            "${_ingrState.value.currentIngredientList.size}"
                )
            }
            is IngredientEvent.RemoveIngredient -> {
                event.ingrList.remove(event.ingredient)
                _ingrState.update { it.copy(currentIngredientList = event.ingrList) }
                Log.i(
                    "ingrEvent", "Ingredient removed: " +
                            "${_ingrState.value.currentIngredientList.size}"
                )
            }
            is IngredientEvent.OnEntryError -> {
                Log.i("ingrEvent", "Error received")
                sendUiEvent(UiEvent.ShowToast(event.message))
            }
            is IngredientEvent.OnFindRecipes -> {
                // It seems that update doesn't remove the old values in the list.
                // Is this because we're updating it with .copy() ?
//                _ingrState.update { it.copy(event.ingrList) }
                Log.i(
                    "ingrEvent", "Final list size: " +
                            "${_ingrState.value.currentIngredientList.size}"
                )
                sendUiEvent(UiEvent.Navigate(Routes.RecipeResultsScreen))
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