package com.example.cheftovers.ui.recipes.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cheftovers.R
import com.example.cheftovers.ui.recipe_details.RecipeDetailsUIState
import com.example.cheftovers.ui.recipe_results.RecipeResultsEvent
import com.example.cheftovers.ui.recipes.search.RecipeCard
import com.example.cheftovers.ui.saved_recipes.SavedRecipesViewModel
import com.example.cheftovers.util.UiEvent

@Composable
fun SavedRecipesScreen(
    viewModel: SavedRecipesViewModel,
    onNavigate: (UiEvent.Navigate) -> Unit,
    modifier: Modifier = Modifier,
    // TODO: Replace w/ the saved recipes from back-end
) {
    val uiState by viewModel.savedRecipesState.collectAsState()
    var descText = "Number of Saved Recipes: ${uiState.savedRecipes.size}"

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RectangleShape
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(R.string.saved_recipes),
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
        if (uiState.savedRecipes.isEmpty()) {
            // Display text describing how to save recipes
            descText = "Tap the star icon on a recipe to save it here!"
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = descText,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
            )
        }
        LazyColumn {
            Log.i(
                "savedRecipesScreen",
                "current saved recipes size: ${uiState.savedRecipes.size}"
            )
            items(items = uiState.savedRecipes) { recipe ->
                RecipeCard(
                    onCardClicked = {
                        viewModel.onEvent(
                            RecipeResultsEvent.OnCardClick(recipe)
                        )
                    },
                    recipe = recipe,
                    modifier = modifier
                )
            }
        }
    }
//    SavedRecipesScreenComponents(
//        recipeUIState = recipeUIState,
//        onCardClicked = recipeViewModel::onCardClicked
//    )
}


//@Composable
//fun SavedRecipesScreenComponents(
//    modifier: Modifier = Modifier,
//    // TODO: Replace w/ the saved recipes from back-end
//    recipeUIState: RecipeDetailsUIState,
//    onCardClicked: () -> Unit
//) {
//}

@Preview(showBackground = true)
@Composable
fun SavedRecipesPreview() {
    SavedRecipesScreen(
        viewModel = SavedRecipesViewModel(),
        onNavigate = {}
    )
}