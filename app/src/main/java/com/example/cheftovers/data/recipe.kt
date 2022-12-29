package com.example.cheftovers.data

data class Recipe(
    val name: String,
    val id: String,
    val time: String,
    val contributor_id: String,
    val submitted: String,
    val tags: List<String>,
    val nutrition: List<String>,
    val n_steps: String,
    val steps: List<String>,
    val description: String,
    val ingredients: List<String>,
    val n_ingredients: String,
    // Default value = optional param
    val image: Int = 0
)

///**
// * Data for each recipe
// */
//data class Recipe(
//    val name: String,
//    val desc: String,
//    val time: String,
//    // TODO: Decide whether the following params should be String or List<String>
//    val ingredients: String,
//    val steps: String,
//    val image: Int
//)
