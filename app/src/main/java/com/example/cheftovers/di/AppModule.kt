package com.example.cheftovers.di

import android.app.Application
import androidx.room.Room
import com.example.cheftovers.data.RecipeDatabase
import com.example.cheftovers.data.RecipeRepository
import com.example.cheftovers.data.RecipeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Dagger Hilt uses this to automatically builds Room database for recipes
     * @param app Project application, in this case RecipeApp
     */
    @Provides
    @Singleton
    fun provideRecipeDatabase(app: Application): RecipeDatabase {
        return Room.databaseBuilder(
            app, RecipeDatabase::class.java, "recipe_db"
        ).build()
    }

    /**
     * Dagger Hilt uses this to automatically create a
     * recipe repository from the database
     * @param db Recipe database, also created by Dagger Hilt
     */
    @Provides
    @Singleton
    fun provideRecipeRepository(db: RecipeDatabase): RecipeRepository {
        return RecipeRepositoryImpl(db.dao)
    }
}