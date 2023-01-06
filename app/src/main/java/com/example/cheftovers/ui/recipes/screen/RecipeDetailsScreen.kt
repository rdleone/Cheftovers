package com.example.cheftovers.ui.recipes.screen

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
import androidx.navigation.compose.rememberNavController
import com.example.cheftovers.R
import com.example.cheftovers.data.Recipe
import com.example.cheftovers.data.recipeSample
import com.example.cheftovers.ui.recipes.viewmodels.RecipeUIState
import com.example.cheftovers.ui.recipes.viewmodels.RecipeViewModel
import com.example.cheftovers.ui.theme.frameModifier
import com.example.cheftovers.ui.theme.recipeDetailsModifier

@Composable
fun RecipeDetails(
    savedRecipeViewModel: RecipeViewModel,
    recipe: Recipe,
) {
    val uiState by savedRecipeViewModel.recipeUIStateStream.collectAsState()
    RecipeDetailsScreen(
        recipe = recipe,
        uiState = uiState,
        onFavoriteClick = savedRecipeViewModel::onFavoriteClick,
        onBackPressed = savedRecipeViewModel::onBackPressed
    )
}

@Composable
fun RecipeDetailsScreen(
    modifier: Modifier = Modifier,
    recipe: Recipe,
    uiState: RecipeUIState,
    onFavoriteClick: (Recipe) -> Unit,
    onBackPressed: () -> Unit
) {
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
                    .clickable { onBackPressed() },
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
                        .clickable { onFavoriteClick(recipe) },
                    contentDescription = ""
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.star_outline),
                    modifier = Modifier
                        .size(36.dp)
                        .clickable { onFavoriteClick(recipe) },
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
                append("\n"+recipe.time)
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
                append("\n"+recipe.steps.joinToString("\n"))
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailsScreenPreview() {
    RecipeDetails(
        recipe = recipeSample(),
        savedRecipeViewModel = RecipeViewModel(rememberNavController())
    )
}