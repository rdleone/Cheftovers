package com.example.cheftovers.data

import android.content.Context
import androidx.room.RoomDatabase
import com.example.cheftovers.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

//class JsonLoader(private val context: Context) : RoomDatabase.Callback() {
//    private val json: Json = Json { ignoreUnknownKeys = true }
//
//    @OptIn(ExperimentalSerializationApi::class)
//    suspend fun loadRecipes(): List<Recipe> = withContext(Dispatchers.IO) {
//        val inputStream = context.resources.openRawResource(R.raw.allrecipes_database)
//        json.decodeFromStream(inputStream)
//
////        val jsonString = context.resources.openRawResource(R.raw.allrecipes_database)
////            .bufferedReader().use { it.readText() }
////        json.decodeFromString(jsonString)
//    }
//}