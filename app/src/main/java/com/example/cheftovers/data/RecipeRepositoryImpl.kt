package com.example.cheftovers.data

/**
 * Implementation of RecipeRepository
 * Once again, this code is redundant now,
 * but it could be useful as the project expands
 * @param dao Recipe data access object
 */
class RecipeRepositoryImpl(
    private val dao: RecipeDao
) : RecipeRepository {

    override suspend fun insertRecipe(recipe: Recipe) {
        dao.insertRecipe(recipe)
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        dao.deleteRecipe(recipe)
    }

    override fun getAllRecipes(): List<Recipe> {
        return dao.getAllRecipes()
    }
}