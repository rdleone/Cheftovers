package com.example.cheftovers.ui.recipe_details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cheftovers.data.Recipe
import com.example.cheftovers.data.RecipeDao
import com.example.cheftovers.util.Routes
import com.example.cheftovers.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonArray
import java.time.Duration
import javax.inject.Inject

/**
 * Handles logic for any buttons on the recipe details screen, specifically the favorite star icon
 */
@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val dao: RecipeDao,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Recipe attribute states
    var recipe by mutableStateOf<Recipe?>(null)
        private set
    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var categories by mutableStateOf(listOf(""))
        private set
    var rating by mutableStateOf(0.0)
        private set
    var prepTime by mutableStateOf(Duration.ZERO)
        private set
    var cookTime by mutableStateOf(Duration.ZERO)
        private set
    var totalTime by mutableStateOf(Duration.ZERO)
        private set
    var ingredients by mutableStateOf(listOf(""))
        private set
    var steps by mutableStateOf(JsonArray(emptyList()))
        private set

    private val _recipeDetailsState = MutableStateFlow(RecipeDetailsUIState())
    val recipeUIState: StateFlow<RecipeDetailsUIState>
        get() = _recipeDetailsState

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val recipeId = savedStateHandle.get<Int>("recipeId")!!
        if (recipeId != -1) {
            viewModelScope.launch(Dispatchers.IO) {
                dao.getRecipeById(recipeId)?.let { recipe ->
                    withContext(Dispatchers.Main) {
                        title = recipe.title
                        description = recipe.description ?: ""
                        categories = recipe.categories ?: listOf("")
                        rating = recipe.rating ?: 0.0
                        prepTime = recipe.prep_time
                        cookTime = recipe.cook_time
                        totalTime = recipe.total_time
                        ingredients = recipe.ingredients
                        steps = recipe.steps
                        Log.i("recipe", title)
                        this@RecipeDetailsViewModel.recipe = recipe
                        _recipeDetailsState.update { it.copy(recipe = recipe) }
                    }
                }
            }
        }
    }

    /**
     * Handles each type of RecipeDetailsEvent.
     */
    fun onEvent(event: RecipeDetailsEvent) {
        when (event) {
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