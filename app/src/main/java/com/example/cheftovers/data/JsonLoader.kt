package com.example.cheftovers.data

import android.content.Context
import com.example.cheftovers.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class JsonLoader(private val context: Context) {
    private val json: Json = Json { ignoreUnknownKeys = true }

    suspend fun loadRecipes(): List<Recipe> = withContext(Dispatchers.IO) {
        val jsonString = context.resources.openRawResource(R.raw.allrecipes_database_sample)
            .bufferedReader().use { it.readText() }
        json.decodeFromString(jsonString)
    }
}