package com.example.cheftovers.ui.recipe_details

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
 * Handles logic for any buttons on the recipe details screen, specifically the favorite star icon
 */
class RecipeDetailsViewModel() : ViewModel() {

    private val _recipeDetailsState = MutableStateFlow(RecipeDetailsUIState())
    val recipeUIState: StateFlow<RecipeDetailsUIState>
        get() = _recipeDetailsState

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        _recipeDetailsState.value = recipeUIState.value.copy()
    }

    /**
     * Handles each type of RecipeDetailsEvent.
     */
    fun onEvent(event: RecipeDetailsEvent) {
        when(event) {
            is RecipeDetailsEvent.OnBack -> {
                sendUiEvent(UiEvent.Navigate(Routes.RecipeResultsScreen))
            }
            is RecipeDetailsEvent.OnFavorite -> {
//                val savedRecipesMutableList = _recipeDetailsState.value.savedRecipes.toMutableList()
                Log.i(
                    "favorite",
                    "# recipes saved before add/remove: ${_recipeDetailsState.value.savedRecipes.size}"
                )
                if (event.savedRecipes.contains(event.recipe)) {
                    event.savedRecipes.remove(event.recipe)
                    _recipeDetailsState.update { it.copy(savedRecipes = event.savedRecipes) }
                } else {
                    event.savedRecipes.add(event.recipe)
                    _recipeDetailsState.update { it.copy(savedRecipes = event.savedRecipes) }
                }
//                _recipeDetailsState.value = recipeUIState.value.copy()
                Log.i("favorite", "# recipes saved: ${_recipeDetailsState.value.savedRecipes.size}")
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