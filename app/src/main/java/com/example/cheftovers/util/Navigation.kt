package com.example.cheftovers.util

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Modified NavigationBar that uses the BottomNavItem data class
 *
 * I totally didn't use a YouTube video such as
 * https://www.youtube.com/watch?v=4xyRnIntwTo
 * to figure out how to do this...
 *
 * @param modifier      Default Modifier
 * @param navController Host NavController
 * @param items         All BottomNavItems to display in the bar
 * @param onItemClick   Navigation function for each item
 */
@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    items: List<BottomNavItem>,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.secondary,
        tonalElevation = 5.dp
    ) {
        items.forEach { item ->
            // The route needs to be hoisted to properly update the UI
            val selected = item.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.onSecondary
                ),
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.name,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            )
        }
    }
}