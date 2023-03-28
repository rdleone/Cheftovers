package com.example.cheftovers.data.recipe

/**
 * Repository for fetching recipes.
 * Unnecessary for the time being, but if we choose to
 * use APIs later on, this will be useful
 */
interface RecipeRepository {

    suspend fun insertRecipe(recipe: Recipe)

    suspend fun insertAll(recipes: List<Recipe>)

    suspend fun deleteRecipe(recipe: Recipe)

    suspend fun getAllRecipes(): List<Recipe>

    fun getRecipeById(id: Int): Recipe?
}