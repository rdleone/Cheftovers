package com.example.cheftovers.data.recipe

import androidx.paging.PagingSource
import androidx.paging.PagingState
import javax.inject.Inject

class RecipePagingSource @Inject constructor(
    private val repository: RecipeRepository
) : PagingSource<Int, Recipe>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        return try {
//            val id = params.key ?: 1
            val recipes = repository.getAllRecipes()
            LoadResult.Page(
                data = recipes,
                prevKey = null,
                nextKey = null
//                if (recipes.isNotEmpty()) id + 1
//                else null
            )
        } catch (e: Exception) {
            LoadResult.Error<Int, Recipe>(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            // Check for first and last page
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

}