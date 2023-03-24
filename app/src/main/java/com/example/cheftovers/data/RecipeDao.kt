package com.example.cheftovers.data

import androidx.room.*

/**
 * Data access object for Recipe data class
 */
@Dao
interface RecipeDao {

    // I'm sure we'll want to add/delete recipes at some point
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipe ORDER BY name ASC")
    fun getAllRecipes(): List<Recipe>

    @Query("SELECT * FROM recipe WHERE id = :id")
    fun getRecipeById(id: Int): Recipe?
}