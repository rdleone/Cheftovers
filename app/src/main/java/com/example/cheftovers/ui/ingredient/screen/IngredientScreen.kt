package com.example.cheftovers.ui.ingredient.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.cheftovers.R
import com.example.cheftovers.ui.ingredient.viewmodels.IngredientUIState
import com.example.cheftovers.ui.ingredient.viewmodels.IngredientViewModel
import com.example.cheftovers.ui.theme.frameModifier

@Composable
fun IngredientScreen(
    modifier: Modifier = Modifier,
    ingredientViewModel: IngredientViewModel
) {
    val uiState by ingredientViewModel.ingredientUIStateStream.collectAsState()
    IngredientScreenComponents(
        uiState = uiState,
        onSearchClick = ingredientViewModel::onSearchClick
    )
}

@Composable
fun IngredientScreenComponents(
    modifier: Modifier = Modifier,
    uiState: IngredientUIState,
    onSearchClick: (MutableList<String>) -> Unit
) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier.frameModifier(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.ingredient_list_head),
                style = MaterialTheme.typography.headlineLarge
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.ingredient_list_desc),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
        // Experimental tag required
        SearchComponents(
            modifier = modifier.fillMaxWidth(),
            hint = stringResource(R.string.ingredient_list_hint),
            uiState = uiState,
            onSearchClick = onSearchClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IngredientScreenPreview() {
    IngredientScreen(
        ingredientViewModel = IngredientViewModel(navController = rememberNavController()),
    )
}
