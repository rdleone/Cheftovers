package com.example.cheftovers.data.recipe

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cheftovers.R
import com.example.cheftovers.util.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

/**
 * The Room database class for the Recipe database
 */
@TypeConverters(Converters::class)
@Database(entities = [Recipe::class], version = 6)
abstract class RecipeDatabase : RoomDatabase() {
    abstract val dao: RecipeDao

    @OptIn(ExperimentalSerializationApi::class)
    private fun prepopulateRecipes(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val json = Json { ignoreUnknownKeys = true }
            context.resources.openRawResource(R.raw.allrecipes_database).use { inputStream ->
                dao.insertAll(json.decodeFromStream(inputStream))
            }
        }
    }

    companion object {
        @Volatile
        private var instance: RecipeDatabase? = null

        fun getInstance(context: Context): RecipeDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_db"
                ).build().also {
                    instance = it
                    it.prepopulateRecipes(context)
                }
            }
        }
    }
}