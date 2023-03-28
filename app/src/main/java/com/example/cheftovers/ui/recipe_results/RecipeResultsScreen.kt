package com.example.cheftovers.ui.recipe_results

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.cheftovers.R
import com.example.cheftovers.data.recipe.Recipe
import com.example.cheftovers.ui.theme.cardImageModifier
import com.example.cheftovers.ui.theme.frameModifier
import com.example.cheftovers.util.UiEvent

@Composable
fun RecipeResultsScreen(
    modifier: Modifier = Modifier,
    viewModel: RecipeResultsViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
) {
//    val uiState by viewModel.recipeResultsState.collectAsState()
    val recipeList = viewModel.recipePager.collectAsLazyPagingItems()

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
            items(items = recipeList) { recipe ->
                recipe?.let {
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
    }
}

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
            .clickable(onClick = { onCardClicked() }),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline,
        ),
        onClick = { onCardClicked() }
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = modifier.cardImageModifier(),
                // TODO: Replace hardcoded image
                painter = painterResource(R.drawable.chicken_noodle_soup),
                contentDescription = null,
            )
            Column {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    text = recipe.title,
                    fontSize = 20.sp
                )
//                Text(
//                    modifier = modifier
//                        .fillMaxWidth()
//                        .padding(4.dp),
//                    text = recipe.total_time.toString(),
//                    fontSize = 20.sp
//                )
                // TODO: Fix spacing inconsistency with below version of time to cook text
                Text(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        text = buildAnnotatedString {
                            val totalTime = recipe.total_time
                            val hrs = totalTime.toMinutes() / 60
                            val mins = totalTime.toMinutes() % 60
                            when (hrs) {
                                0L -> append("\n$mins minutes")
                                1L -> append("\n$hrs hour, $mins minutes")
                                else -> append("\n$hrs hours, $mins minutes")
                            }
                        },
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
        viewModel = hiltViewModel(),
        onNavigate = {}
    )
}