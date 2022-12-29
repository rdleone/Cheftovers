package com.example.cheftovers.ui.recipes.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.cheftovers.data.recipeSample
import com.example.cheftovers.ui.recipes.viewmodels.RecipeUIState
import com.example.cheftovers.ui.recipes.viewmodels.RecipeViewModel
import com.example.cheftovers.ui.theme.cardImageModifier
import com.example.cheftovers.ui.theme.frameModifier

@Composable
fun RecipeResultsScreen(
    modifier: Modifier = Modifier,
    // TODO: Replace dummy data
    recipeViewModel: RecipeViewModel,
) {
    val uiState by recipeViewModel.recipeUIStateStream.collectAsState()
    RecipeResultsComponents(
        uiState = uiState,
        onCardClicked = recipeViewModel::onCardClicked
    )
}

@Composable
fun RecipeResultsComponents(
    modifier: Modifier = Modifier,
    uiState: RecipeUIState,
    // TODO: Replace dummy data
    onCardClicked: () -> Unit,
    recipes: List<Recipe> = List(10) { recipeSample() }
) {
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
            items(items = recipes) { recipe ->
                RecipeCard(onCardClicked = onCardClicked, recipe = recipe)
            }
        }
    }
}

/**
 * Card displaying recipe data. When clicked, navigates user to
 * its recipe details page.
 *
 * @param modifier      Default Modifier
 * @param navController Host NavController
 * @param recipe        Recipe to display
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeCard(
    modifier: Modifier = Modifier,
    onCardClicked: () -> Unit,
    recipe: Recipe
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
                painter = painterResource(recipe.image),
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
//    RecipeResultsScreen(navController = rememberNavController())
}