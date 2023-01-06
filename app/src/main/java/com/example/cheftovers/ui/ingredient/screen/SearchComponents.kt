package com.example.cheftovers.ui.ingredient.screen

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
import com.example.cheftovers.ui.ingredient.viewmodels.IngredientUIState

/**
 * Contains the search bar, submit button, and current list
 * of ingredients for the Ingredient Search screen.
 *
 * @param modifier          Default modifier
 * @param hint              Placeholder text in search bar
 * @param list              Active list of ingredients on this screen
 * @param uiState           The finalized state of the ingredient list
 * @param onSearchClick     Executes the recipe search with the current ingredient list
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchComponents(
    modifier: Modifier = Modifier,
    hint: String,
    uiState: IngredientUIState,
    onSearchClick: (MutableList<String>) -> Unit
) {
    // Current ingredient list
    val list = remember { mutableStateListOf<String>() }
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .shadow(4.dp, CircleShape),
            value = text,
            placeholder = { Text(text = hint) },
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
                    } else if (list.contains(text.lowercase())) {
                        isDuplicate = true
                    }
                    // Max size is 20, can be changed later
                    else if (list.size == 10) {
                        isMaxListSize = true
                    } else {
                        list.add(text.lowercase())
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
                    } else if (list.contains(text.lowercase())) {
                        isDuplicate = true
                    }
                    // Max size is 10, can be changed later
                    else if (list.size == 10) {
                        isMaxListSize = true
                    } else {
                        list.add(text.lowercase())
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
            modifier = Modifier.weight(1f),
            columns = GridCells.Fixed(2),
        ) {
            items(list) { item ->
                if (isDuplicate) {
                    Toast.makeText(
                        LocalContext.current,
                        stringResource(R.string.ingredient_alert_title1),
                        Toast.LENGTH_LONG
                    ).show()
                    isDuplicate = false
                } else if (isMaxListSize) {
                    Toast.makeText(
                        LocalContext.current,
                        stringResource(R.string.ingredient_alert_title2),
                        Toast.LENGTH_LONG
                    ).show()
                    isMaxListSize = false
                }
                // Each ingredient can be clicked to be removed
                ClickableText(
                    modifier = Modifier
                        .padding(10.dp),
                    text = AnnotatedString(item),
                    style = MaterialTheme.typography.bodyLarge +
                            TextStyle(fontSize = 24.sp, textAlign = TextAlign.Center),
                    onClick = { list.remove(item) }
                )
            }
        }
        // Button to initiate recipe search with current ingredient list
        Button(
            modifier = Modifier
                .padding(16.dp),
            onClick = {
                if (list.size == 0) {
                    isListEmpty = true
                } else {
                    // Add ingredients from List to UIState
                    for (item in list) {
                        uiState.currentIngredientList.add(item)
                    }
                    onSearchClick(uiState.currentIngredientList)
                }
            },
            shape = RectangleShape
        ) {
            Text(text = stringResource(R.string.ingredient_results_button))
            if (isListEmpty) {
                Toast.makeText(
                    LocalContext.current,
                    stringResource(R.string.ingredient_alert_title3),
                    Toast.LENGTH_LONG
                ).show()
                isListEmpty = false
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchComponents() {
    IngredientScreenComponents(
        onSearchClick = {},
        uiState = IngredientUIState()
    )
}