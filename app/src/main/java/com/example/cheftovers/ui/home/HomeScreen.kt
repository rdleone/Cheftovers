package com.example.cheftovers.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cheftovers.R
import com.example.cheftovers.util.UiEvent

/**
 * Home screen that displays the navigation bar, the title screen,
 * a general screen that displays text and instructions on using the app
 *
 * @param viewModel Top level screen ViewModel
 * @param onNavigate Used by NavHost in nav map
 * @param modifier Default Modifier to allow compose components to have modifications
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigate: (UiEvent.Navigate) -> Unit,
    modifier: Modifier = Modifier,
) {
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
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.welcome),
            style = MaterialTheme.typography.headlineMedium
        )
        Image(
            modifier = modifier.padding(24.dp),
            painter = painterResource(R.drawable.cheftovers_icon_512),
            contentDescription = null
        )
        Button(
            onClick = { viewModel.onEvent(HomeEvent.OnRecipeSearch) },
            shape = RectangleShape,
            elevation = ButtonDefaults.buttonElevation(4.dp)
        ) {
            Text(
                text = stringResource(R.string.recipe_search),
                fontSize = 20.sp,
            )
        }
        Column(
            modifier
                .padding(vertical = 16.dp)
                .background(Color.Yellow),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = modifier
                    .padding(8.dp),
                text = stringResource(R.string.instructions_header),
                fontSize = 20.sp
            )
            Text(
                modifier = modifier
                    .padding(8.dp),
                text = stringResource(R.string.instructions_body),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    HomeScreen(
        viewModel = HomeViewModel(),
        onNavigate = {}
    )
}