package com.example.cheftovers.di

import android.app.Application
import androidx.room.Room
import com.example.cheftovers.data.JsonLoader
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
     * Dagger Hilt uses this to automatically build Room database for recipes
     * @param app Project application, in this case RecipeApp
     */
    @Provides
    @Singleton
    fun provideRecipeDatabase(app: Application): RecipeDatabase {
        return Room.databaseBuilder(app, RecipeDatabase::class.java, "recipe_db")
            .fallbackToDestructiveMigration()
            .build()
//        return Room.databaseBuilder(
//            app, RecipeDatabase::class.java, "recipe_db"
//        )
//            .createFromAsset("database/recipe_db.db")
//            .fallbackToDestructiveMigration()
//            .build()
    }

    /**
     * Dagger Hilt uses this to provide the Recipe DAO
     * @param db Recipe database created by Dagger Hilt
     */
    @Provides
    @Singleton
    fun provideRecipeDao(db: RecipeDatabase) = db.dao

    /**
     * Dagger Hilt uses this to automatically create a
     * recipe repository from the database
     * @param db Recipe database created by Dagger Hilt
     */
    @Provides
    @Singleton
    fun provideRecipeRepository(app: Application, db: RecipeDatabase): RecipeRepository {
        return RecipeRepositoryImpl(db.dao, JsonLoader(app))
    }
}