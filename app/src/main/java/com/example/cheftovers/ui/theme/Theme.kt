package com.example.cheftovers.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFCE6400),
    secondary = Color(0xFFD78300),
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = OrangePrimary,
    secondary = OrangeSecondary,
    tertiary = Pink40,

    // Other default colors to override
    background = OrangeBackground,
    surface = OrangeBackground,
    onPrimary = Color.White,
    onPrimaryContainer = Color.Black,
    onSecondary = Color(0xFFFFD4CC),
    onSecondaryContainer = OrangeOnContainer,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),

)

@Composable
fun CheftoversTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

/**
 * Intended for Columns, Rows, Boxes that need a simple outline
 */
fun Modifier.frameModifier(): Modifier = composed {
    this.fillMaxWidth()
        .border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline,
            shape = RectangleShape,
        )
}

/**
 * Image size for the Recipe CARD
 */
fun Modifier.cardImageModifier(): Modifier = composed {
    this.size(96.dp)
}

// TODO: Decide an image size for the recipe details screen

/**
 * Intended for all recipe composables in its details page
 */
fun Modifier.recipeDetailsModifier(): Modifier = composed {
    this.fillMaxWidth()
        .padding(16.dp)
}