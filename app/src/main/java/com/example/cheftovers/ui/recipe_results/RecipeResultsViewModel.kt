package com.example.cheftovers.ui.recipe_results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cheftovers.data.RecipeRepository
import com.example.cheftovers.util.Routes
import com.example.cheftovers.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Handles logic for the Recipe Results screen, namely launching
 * a recipe's details screen from its card.
 */
@HiltViewModel
class RecipeResultsViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _recipeResultsState = MutableStateFlow(RecipeResultsUIState())
    val recipeResultsState: StateFlow<RecipeResultsUIState>
        get() = _recipeResultsState

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            repository.preloadData()
            _recipeResultsState.update { it.copy(recipes = repository.getAllRecipes()) }
        }
    }

    /**
     * Handles each type of RecipeResultsEvent
     */
    fun onEvent(event: RecipeResultsEvent) {
        when (event) {
            is RecipeResultsEvent.OnCardClick -> {
                sendUiEvent(
                    UiEvent.Navigate(
                        Routes.RecipeDetailsScreen + "?recipeId=${event.recipe.id}"
                    )
                )
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