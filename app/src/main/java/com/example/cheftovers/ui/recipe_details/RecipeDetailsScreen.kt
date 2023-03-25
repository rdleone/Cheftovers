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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cheftovers.R
import com.example.cheftovers.ui.theme.frameModifier
import com.example.cheftovers.ui.theme.recipeDetailsModifier
import com.example.cheftovers.util.UiEvent
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject

/**
 * Screen that displays the expanded details of the recipe selected
 * by the user, as well as an option to save the recipe.
 *
 * @param viewModel Top level screen ViewModel
 * @param onNavigate Used by NavHost in nav map
 * @param modifier Default Modifier to allow compose components to have modifications
 */
@Composable
fun RecipeDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: RecipeDetailsViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
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
                text = uiState.recipe.title,
                modifier = Modifier
                    .weight(1f),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge
            )
            if (uiState.savedRecipes.contains(uiState.recipe)) {
                Icon(
                    painter = painterResource(id = R.drawable.star_filled),
                    modifier = Modifier
                        .size(36.dp)
                        .clickable {
                            viewModel.onEvent(
                                RecipeDetailsEvent.OnFavorite(uiState.recipe, uiState.savedRecipes)
                            )
                        },
                    contentDescription = ""
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.star_outline),
                    modifier = Modifier
                        .size(36.dp)
                        .clickable {
                            viewModel.onEvent(
                                RecipeDetailsEvent.OnFavorite(uiState.recipe, uiState.savedRecipes)
                            )
                        },
                    contentDescription = ""
                )
            }
        }
        // Recipe Image
        Image(
            modifier = modifier.recipeDetailsModifier(),
            painter = painterResource(R.drawable.chicken_noodle_soup),
            contentDescription = null
        )
        // Description
        Text(
            modifier = modifier.recipeDetailsModifier(),
            text = uiState.recipe.description!!,
        )
        // Total Time to Cook
        Text(
            modifier = modifier.recipeDetailsModifier(),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)) {
                    append(stringResource(R.string.time_to_cook))
                }
                val totalTime = uiState.recipe.total_time
                val hrs = totalTime.toMinutes() / 60
                val mins = totalTime.toMinutes() % 60
                when (hrs) {
                    0L -> append("\n$mins minutes")
                    1L -> append("\n$hrs hour, $mins minutes")
                    else -> append("\n$hrs hours, $mins minutes")
                }
            }
        )
        Text(
            modifier = modifier.recipeDetailsModifier(),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)) {
                    append(stringResource(R.string.ingredients))
                }
                append("\n" + uiState.recipe.ingredients.joinToString("\n"))
            }
        )
        Text(
            modifier = modifier.recipeDetailsModifier(),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)) {
                    append(stringResource(R.string.instructions))
                }
                val jsonArray = uiState.recipe.steps
                for(el in jsonArray) {
                    val obj = el.jsonObject
                    val step = obj["step"]
                    val instruction = obj["instruction"].toString().removeSurrounding("\"")
                    append("\n$step. $instruction\n")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailsScreenPreview() {
    RecipeDetailsScreen(
        viewModel = hiltViewModel(),
        onNavigate = {},
    )
}