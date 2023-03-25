package com.example.cheftovers.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of RecipeRepository
 * Once again, this code is redundant now,
 * but it could be useful as the project expands
 * @param dao Recipe data access object
 */
class RecipeRepositoryImpl @Inject constructor(
    private val dao: RecipeDao,
    private val jsonLoader: JsonLoader
) : RecipeRepository {

    override suspend fun insertRecipe(recipe: Recipe) {
        dao.insertRecipe(recipe)
    }

    override suspend fun insertAll(recipes: List<Recipe>) {
        dao.insertAll(recipes)
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        dao.deleteRecipe(recipe)
    }

    override fun getAllRecipes(): List<Recipe> {
        return dao.getAllRecipes()
    }

    override fun getRecipeById(id: Int): Recipe? {
        return dao.getRecipeById(id)
    }

    override suspend fun preloadData() = withContext(Dispatchers.IO) {
        val recipes = jsonLoader.loadRecipes()
        dao.insertAll(recipes)
    }
}