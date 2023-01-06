package com.example.cheftovers.data

import androidx.room.*

/**
 * Data access object for Recipe data class
 */
@Dao
interface RecipeDao {

    // I'm sure we'll want to add/delete recipes at some point
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipe WHERE id = :id")
    suspend fun getRecipeById(id: Int): Recipe?
}