package com.example.cheftovers.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * Defines a recipe based on relevant characteristics, compatible with Room
 * @property name Official name
 * @property description Description, highlights
 * @property prep_time Time to prep
 * @property cook_time Time to cook
 * @property total_time Combined prep and cook time
 * @property ingredients List of ingredients and their portions
 * @property steps List of instructions
 * @property images List of image IDs
 * @property id Database primary key
 */
@Serializable
@Entity
data class Recipe(
    val name: String = "",
    val description: String? = "",
    val categories: List<String>? = listOf(),
    val rating: Double? = 0.0,
    val prep_time: Int? = 0,
    val cook_time: Int? = 0,
    val total_time: Int = 0,
    val ingredients: List<String>,
//    val steps: JsonElement = JsonPrimitive(""),
    val steps: List<String>,
    val images: List<String>? = listOf(),
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
