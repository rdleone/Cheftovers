package com.example.cheftovers.ui.recipes.screen

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cheftovers.ui.recipes.search.RecipeCard
import com.example.cheftovers.ui.recipes.viewmodels.RecipeUIState
import com.example.cheftovers.ui.recipes.viewmodels.RecipeViewModel
import com.example.cheftovers.R

@Composable
fun SavedRecipesScreen(
    modifier: Modifier = Modifier,
    recipeViewModel: RecipeViewModel,
    recipeUIState: RecipeUIState
    // TODO: Replace w/ the saved recipes from back-end
) {
//    val recipeUIState by recipeViewModel.recipeUIStateStream.collectAsState()
    SavedRecipesScreenComponents(
        recipeUIState = recipeUIState,
        onCardClicked = recipeViewModel::onCardClicked
    )
}


@Composable
fun SavedRecipesScreenComponents(
    modifier: Modifier = Modifier,
    // TODO: Replace w/ the saved recipes from back-end
    recipeUIState: RecipeUIState,
    onCardClicked: () -> Unit
) {

    var descText = "Number of Saved Recipes: ${recipeUIState.savedRecipes.size}"

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
        if(recipeUIState.savedRecipes.isEmpty()) {
            // Display text describing how to save recipes
            descText = "Tap the star icon on a recipe to save it here!"
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment =  Alignment.CenterVertically
        ) {
            Text(
                text = descText,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
            )
        }


//        To use for testing
//        val recipes: List<Recipe> = List(3) { recipeSample() }

        LazyColumn {
            Log.i("savedRecipesScreen", "current saved recipes size: ${recipeUIState.savedRecipes.size}")
            items(items = recipeUIState.savedRecipes) { recipe ->
                RecipeCard(onCardClicked = onCardClicked, recipe = recipe)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SavedRecipesPreview() {
//    SavedRecipesScreenComponents(navController = rememberNavController())
}