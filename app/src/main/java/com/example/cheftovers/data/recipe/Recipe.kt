package com.example.cheftovers.data.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cheftovers.data.DurationSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import java.time.Duration

/**
 * The Room database entity. Defines a recipe based on characteristics from
 * the original JSON document
 * @property title Official name
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
    var title: String,
    var description: String?,
    var categories: List<String>?,
    var rating: Double?,
    @Serializable(with = DurationSerializer::class)
    var prep_time: Duration,
    @Serializable(with = DurationSerializer::class)
    var cook_time: Duration,
    @Serializable(with = DurationSerializer::class)
    var total_time: Duration,
    var ingredients: List<String>,
    var steps: JsonArray,
    var images: List<String>?,
    @PrimaryKey(autoGenerate = true)
    var id: Int
) {
    constructor() : this(
        title = "",
        description = "",
        categories = listOf(""),
        rating = 0.0,
        prep_time = Duration.ZERO,
        cook_time = Duration.ZERO,
        total_time = Duration.ZERO,
        ingredients = listOf(""),
        steps = JsonArray(emptyList()),
        images = listOf(""),
        id = -1
    )
}
