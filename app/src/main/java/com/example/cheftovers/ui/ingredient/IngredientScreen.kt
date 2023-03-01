package com.example.cheftovers.ui.ingredient

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cheftovers.R
import com.example.cheftovers.ui.theme.frameModifier
import com.example.cheftovers.util.UiEvent

/**
 * Ingredient List screen in which users create a list of ingredients
 * to use in the Recipe Search.
 *
 * @param onNavigate Used by NavHost in nav map
 * @param viewModel Top level screen ViewModel
 * @param modifier Default modifier
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientScreen(
    viewModel: IngredientViewModel,
    onNavigate: (UiEvent.Navigate) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val uiState by viewModel.ingrState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowToast -> {
                    Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> Unit
            }
        }
    }
//    IngredientScreenComponents(
//        uiState = uiState,
//        onEvent = viewModel::onEvent,
//        modifier = modifier
//    )
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
//        // Experimental tag required
//        SearchComponents(
//            uiState = uiState,
//            onEvent = viewModel::onEvent,
//            modifier = modifier,
//        )
        // Current ingredient list
//        val list = remember { mutableStateListOf<String>() }
        // User's current text input
        var text by rememberSaveable { mutableStateOf("") }
        // Error conditions
        var isMaxListSize by rememberSaveable { mutableStateOf(false) }
        var isDuplicate by rememberSaveable { mutableStateOf(false) }
        var isListEmpty by rememberSaveable { mutableStateOf(false) }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Search Bar
            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .shadow(4.dp, CircleShape),
                value = text,
                placeholder = { Text(text = stringResource(R.string.ingredient_list_hint)) },
                onValueChange = { text = it },
                maxLines = 1,
                singleLine = true,
                shape = CircleShape,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary
                ),
                // Button to submit text entry
                trailingIcon = @Composable {
                    IconButton(onClick = {
                        if (text.matches(Regex(" *"))) {
                            // ignore empty entry
                        } else if (uiState.currentIngredientList.contains(text.lowercase())) {
                            isDuplicate = true
                        }
                        // Max size is 20, can be changed later
                        else if (uiState.currentIngredientList.size == 10) {
                            isMaxListSize = true
                        } else {
//                            list.add(text.lowercase())
                            viewModel.onEvent(
                                IngredientEvent.AddIngredient(
                                    text.lowercase(), uiState.currentIngredientList
                                )
                            )
                            text = ""
                        }
                    }) {
                        Icon(
                            Icons.Default.ArrowForward,
                            contentDescription = "",
                            tint = Color.Black
                        )
                    }
                },
                // Creates 'Done' option on keyboard to submit text entry
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (text.matches(Regex(" *"))) {
                            // ignore empty entry
                        } else if (uiState.currentIngredientList.contains(text.lowercase())) {
                            isDuplicate = true
                        }
                        // Max size is 10, can be changed later
                        else if (uiState.currentIngredientList.size == 10) {
                            isMaxListSize = true
                        } else {
//                            list.add(text.lowercase())
                            viewModel.onEvent(
                                IngredientEvent.AddIngredient(
                                    text.lowercase(), uiState.currentIngredientList
                                )
                            )
                            text = ""
                        }
                    }),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = true,
                    imeAction = ImeAction.Done
                )
            )
        }
        Text(
            text = stringResource(R.string.ingredient_current_list),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Current ingredient list displayed in 2 columns
            LazyVerticalGrid(
                modifier = modifier.weight(1f),
                columns = GridCells.Fixed(2),
            ) {
                items(uiState.currentIngredientList) { item ->
                    if (isDuplicate) {
                        viewModel.onEvent(
                            IngredientEvent.onEntryError(
                                stringResource(R.string.ingredient_alert_title1)
                            )
                        )
                        isDuplicate = false
                    } else if (isMaxListSize) {
                        viewModel.onEvent(
                            IngredientEvent.onEntryError(
                                stringResource(R.string.ingredient_alert_title2)
                            )
                        )
                        isMaxListSize = false
                    }
                    // Each ingredient can be clicked to be removed
                    ClickableText(
                        modifier = modifier
                            .padding(10.dp),
                        text = AnnotatedString(item),
                        style = MaterialTheme.typography.bodyLarge +
                                TextStyle(fontSize = 24.sp, textAlign = TextAlign.Center),
                        onClick = {
//                            list.remove(item)
                            viewModel.onEvent(
                                IngredientEvent.RemoveIngredient(
                                    item,
                                    uiState.currentIngredientList
                                )
                            )
                        }
                    )
                }
            }
            // Button to initiate recipe search with current ingredient list
            Button(
                modifier = modifier
                    .padding(16.dp),
                onClick = {
                    if (uiState.currentIngredientList.size == 0) {
                        isListEmpty = true
                    } else {
                        viewModel.onEvent(
                            IngredientEvent.onFindRecipes(uiState.currentIngredientList)
                        )
                    }
                },
                shape = RectangleShape
            ) {
                Text(text = stringResource(R.string.ingredient_results_button))
                if (isListEmpty) {
                    viewModel.onEvent(
                        IngredientEvent.onEntryError(
                            stringResource(R.string.ingredient_alert_title3)
                        )
                    )
                    isListEmpty = false
                }
            }
        }
    }
}

//@Composable
//fun IngredientScreenComponents(
//    uiState: IngredientUIState,
//    onEvent: (IngredientEvent) -> Unit,
//    modifier: Modifier,
//) {
//
//    Column(
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Row(
//            modifier = modifier.frameModifier(),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = stringResource(R.string.ingredient_list_head),
//                style = MaterialTheme.typography.headlineLarge
//            )
//        }
//        Row(
//            modifier = modifier
//                .fillMaxWidth()
//                .padding(12.dp),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = stringResource(R.string.ingredient_list_desc),
//                style = MaterialTheme.typography.bodyLarge,
//                textAlign = TextAlign.Center,
//                fontSize = 20.sp
//            )
//        }
//        // Experimental tag required
//        SearchComponents(
//            uiState = uiState,
//            onEvent = onEvent,
//            modifier = modifier,
//        )
//    }
//}

@Preview(showBackground = true)
@Composable
fun IngredientScreenPreview() {
    IngredientScreen(
        viewModel = IngredientViewModel(),
        onNavigate = {}
    )
}
