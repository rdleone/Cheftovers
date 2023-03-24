package com.example.cheftovers.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cheftovers.util.Converters

@TypeConverters(Converters::class)
@Database(entities = [Recipe::class], version = 2)
abstract class RecipeDatabase: RoomDatabase() {
    abstract val dao: RecipeDao
}