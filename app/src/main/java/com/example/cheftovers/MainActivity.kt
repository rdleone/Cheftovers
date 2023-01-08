package com.example.cheftovers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.cheftovers.util.BottomNavBar
import com.example.cheftovers.util.BottomNavItem
import com.example.cheftovers.util.Navigation
import com.example.cheftovers.util.Routes
import com.example.cheftovers.ui.theme.CheftoversTheme
import com.example.cheftovers.ui.theme.secularoneFamily
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CheftoversTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Scaffold provides a neat little slot specifically
                    // for bottom and top bars
                    Scaffold(
                        topBar = {
                            CenterAlignedTopAppBar(
                                title = {
                                    Text(
                                        text = stringResource(R.string.app_name).uppercase(),
                                        fontSize = 32.sp,
                                        fontFamily = secularoneFamily,
                                        letterSpacing = 4.sp
                                    )
                                },
                                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.secondary
                                )
                            )
                        },
                        bottomBar = {
                            BottomNavBar(
                                navController = navController,
                                items = listOf(
                                    BottomNavItem(
                                        name = stringResource(R.string.home),
                                        route = Routes.HomeScreen,
                                        icon = Icons.Default.Home
                                    ),
                                    BottomNavItem(
                                        name = stringResource(R.string.recipe_search),
                                        route = Routes.IngredientScreen,
                                        icon = Icons.Default.Search
                                    ),
                                    BottomNavItem(
                                        name = stringResource(R.string.saved_recipes),
                                        route = Routes.SavedRecipesScreen,
                                        icon = Icons.Default.Star
                                    ),
                                ),
                                onItemClick = { navController.navigate(it.route) })
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            Navigation(navController = navController)
                        }
                    }
                }
            }
        }
    }
}