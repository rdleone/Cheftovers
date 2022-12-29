package com.example.cheftovers.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cheftovers.navigation.ScreenRoute
import com.example.cheftovers.R
import com.example.cheftovers.ui.home.viewmodels.HomeScreenViewModel


/**
 * Home screen that displays the navigation bar, the title screen,
 * a general screen that displays text and instructions on using the app
 *
 * @param modifier      default Modifier to allow compose components to have modifications
 * @param navController navigation controller that allows movement across screens
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeScreenViewModel: HomeScreenViewModel
) {
    val onIngredientSearchClick: () -> Unit = homeScreenViewModel::onIngredientSearchClick

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                // NOTE: Paddings may need to be adjusted for smaller phones
                Modifier.padding(vertical = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.welcome),
                    style = MaterialTheme.typography.headlineMedium
                )
                Image(
                    modifier = Modifier.padding(24.dp),
                    painter = painterResource(R.drawable.cheftovers_icon_512),
                    contentDescription = null
                )
                Button(
                    onClick = {
                        onIngredientSearchClick()
                    },
                    shape = RectangleShape,
                    elevation = ButtonDefaults.buttonElevation(4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.recipe_search),
                        fontSize = 20.sp,
                    )
                }
//                Spacer(Modifier.padding(28.dp))
                Column(
                    Modifier
                        .padding(vertical = 16.dp)
                        .background(Color.Yellow),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = stringResource(R.string.instructions_header),
                        fontSize = 20.sp
                    )
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = stringResource(R.string.instructions_body),
                        textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    HomeScreen(
        homeScreenViewModel = HomeScreenViewModel(
            navController = rememberNavController()
        )
    )
}