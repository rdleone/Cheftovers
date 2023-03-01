package com.example.cheftovers.ui.recipe_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cheftovers.R
import com.example.cheftovers.data.Recipe
import com.example.cheftovers.data.recipeSample
import com.example.cheftovers.ui.theme.frameModifier
import com.example.cheftovers.ui.theme.recipeDetailsModifier
import com.example.cheftovers.util.UiEvent

/**
 * Screen that displays the expanded details of the recipe selected
 * by the user, as well as an option to save the recipe.
 *
 * @param viewModel Top level screen ViewModel
 * @param onNavigate Used by NavHost in nav map
 * @param recipe The selected recipe whose details populate this screen
 * @param modifier Default Modifier to allow compose components to have modifications
 */
@Composable
fun RecipeDetailsScreen(
    viewModel: RecipeDetailsViewModel,
    onNavigate: (UiEvent.Navigate) -> Unit,
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.recipeUIState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .frameModifier(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                modifier = Modifier
                    .size(36.dp)
                    .clickable { viewModel.onEvent(RecipeDetailsEvent.OnBack) },
                contentDescription = ""
            )
            Text(
                text = recipe.name,
                modifier = Modifier
                    .weight(1f),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge
            )
            if (uiState.savedRecipes.contains(recipe)) {
                Icon(
                    painter = painterResource(id = R.drawable.star_filled),
                    modifier = Modifier
                        .size(36.dp)
                        .clickable { viewModel.onEvent(
                            RecipeDetailsEvent.OnFavorite(recipe, uiState.savedRecipes)) },
                    contentDescription = ""
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.star_outline),
                    modifier = Modifier
                        .size(36.dp)
                        .clickable { viewModel.onEvent(
                            RecipeDetailsEvent.OnFavorite(recipe, uiState.savedRecipes)) },
                    contentDescription = ""
                )
            }
        }
        Image(
            modifier = modifier.recipeDetailsModifier(),
            painter = painterResource(recipe.images[0]),
            contentDescription = null
        )
        Text(
            modifier = modifier.recipeDetailsModifier(),
            text = recipe.desc,
        )
        Text(
            modifier = modifier.recipeDetailsModifier(),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)) {
                    append(stringResource(R.string.time_to_cook))
                }
                append("\n" + recipe.time)
            }
        )
        Text(
            modifier = modifier.recipeDetailsModifier(),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)) {
                    append(stringResource(R.string.ingredients))
                }
                append("\n" + recipe.ingr.joinToString("\n"))
            }
        )
        Text(
            modifier = modifier.recipeDetailsModifier(),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)) {
                    append(stringResource(R.string.instructions))
                }
                append("\n" + recipe.steps.joinToString("\n"))
            }
        )
    }
//    RecipeDetailsComponents(
//        recipe = recipe,
//        uiState = uiState,
//        onFavoriteClick = viewModel::onFavoriteClick,
//        onBackPressed = viewModel::onBackPressed
//    )
}

//@Composable
//fun RecipeDetailsComponents(
//    modifier: Modifier = Modifier,
//    recipe: Recipe,
//    uiState: RecipeDetailsUIState,
//    onFavoriteClick: (Recipe) -> Unit,
//    onBackPressed: () -> Unit
//) {
//
//}

@Preview(showBackground = true)
@Composable
fun RecipeDetailsScreenPreview() {
    RecipeDetailsScreen(
        viewModel = RecipeDetailsViewModel(),
        onNavigate = {},
        recipe = recipeSample()
    )
}