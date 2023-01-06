package com.example.cheftovers.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Defines a recipe based on relevant characteristics, compatible with Room
 * @property name Official name of recipe
 * @property desc Description tied to recipe
 * @property time Time to cook for recipe
 * @property ingr List of ingredients for recipe
 * @property steps List of instructions to make recipe
 * @property images List of image IDs for recipe
 * @property isSaved Indicates if recipe added in Saved Recipes
 * @property id Key for Room functionality
 */
@Entity
data class Recipe(
    val name: String,
    val desc: String,
    val time: String,
    val ingr: List<String>,
    val steps: List<String>,
    val images: List<Int>,
    val isSaved: Boolean,
    @PrimaryKey val id: String = name+time
)
