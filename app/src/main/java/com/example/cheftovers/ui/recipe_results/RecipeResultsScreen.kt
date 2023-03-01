package com.example.cheftovers.ui.recipes.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cheftovers.R
import com.example.cheftovers.data.Recipe
import com.example.cheftovers.ui.recipe_results.RecipeResultsEvent
import com.example.cheftovers.ui.recipe_results.RecipeResultsViewModel
import com.example.cheftovers.ui.theme.cardImageModifier
import com.example.cheftovers.ui.theme.frameModifier
import com.example.cheftovers.util.UiEvent

@Composable
fun RecipeResultsScreen(
    // TODO: Replace dummy data
    viewModel: RecipeResultsViewModel,
    onNavigate: (UiEvent.Navigate) -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.recipeResultsState.collectAsState()
//    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.frameModifier(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.recipe_results_head),
                style = MaterialTheme.typography.headlineLarge
            )
        }
        LazyColumn {
            items(items = uiState.recipes) { recipe ->
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

//    RecipeResultsComponents(
//        uiState = uiState,
//        onCardClicked = recipeViewModel::onCardClicked
//    )
}

//@Composable
//fun RecipeResultsComponents(
//    modifier: Modifier = Modifier,
//    uiState: RecipeUIState,
//    // TODO: Replace dummy data
//    onCardClicked: () -> Unit,
//    recipes: List<Recipe> = List(10) { recipeSample() }
//) {
//    Column(modifier = modifier.fillMaxSize()) {
//        Row(
//            modifier = Modifier.frameModifier(),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = stringResource(R.string.recipe_results_head),
//                style = MaterialTheme.typography.headlineLarge
//            )
//        }
//        LazyColumn {
//            items(items = recipes) { recipe ->
//                RecipeCard(onCardClicked = onCardClicked, recipe = recipe)
//            }
//        }
//    }
//}

/**
 * Card displaying recipe data. When clicked, navigates user to
 * its recipe details page.
 *
 * @param onCardClicked Handles navigation to corresponding Recipe Details screen
 * @param recipe Recipe represented by this card
 * @param modifier Default modifier
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeCard(
    onCardClicked: () -> Unit,
    recipe: Recipe,
    modifier: Modifier,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(
                onClick = { onCardClicked() }
            ),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary
        ),
        elevation = CardDefaults.cardElevation(5.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline,
        ),
        onClick = {
            onCardClicked()
        }
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = modifier.cardImageModifier(),
                // TODO: Replace hardcoded image
                painter = painterResource(recipe.images[0]),
                contentDescription = null,
            )
            Column {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    text = recipe.name,
                    fontSize = 20.sp
                )
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    text = recipe.time,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeResultsPreview() {
    RecipeResultsScreen(
        viewModel = RecipeResultsViewModel(),
        onNavigate = {}
    )
}