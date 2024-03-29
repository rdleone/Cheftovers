package com.example.cheftovers.di

import android.app.Application
import com.example.cheftovers.data.recipe.RecipeDatabase
import com.example.cheftovers.data.recipe.RecipeRepository
import com.example.cheftovers.data.recipe.RecipeRepositoryImpl
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
//        return Room.databaseBuilder(app, RecipeDatabase::class.java, "recipe_db")
//            .fallbackToDestructiveMigration()
//            .build()
        return RecipeDatabase.getInstance(app)
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
        return RecipeRepositoryImpl(db.dao)
    }
}