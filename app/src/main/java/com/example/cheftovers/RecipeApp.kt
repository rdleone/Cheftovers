package com.example.cheftovers

import android.app.Application
import com.example.cheftovers.data.recipe.RecipeDatabase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class RecipeApp : Application() {
    @Inject
    lateinit var recipeDB: RecipeDatabase
}