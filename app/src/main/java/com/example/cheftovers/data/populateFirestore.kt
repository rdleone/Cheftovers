package com.example.cheftovers.data

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.cheftovers.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.BufferedReader

fun populateFirestore(context: Context) {
    val br = context.resources.openRawResource(R.raw.small_recipes).bufferedReader()
    val listOfRecipes = readCSV(br)

    val db = Firebase.firestore
//    db.useEmulator("10.0.2.2", 8080)
    for (rcpe in listOfRecipes) {
        val data = hashMapOf(
            "name" to rcpe.name,
            "id" to rcpe.id,
            "time" to rcpe.time,
            "contributor_id" to rcpe.contributor_id,
            "submitted" to rcpe.submitted,
            "tags" to rcpe.tags,
            "nutrition" to rcpe.nutrition,
            "n_steps" to rcpe.n_steps,
            "steps" to rcpe.steps,
            "description" to rcpe.description,
            "ingredients" to rcpe.ingredients,
            "n_ingredients" to rcpe.n_ingredients,
        )

        db.collection("recipes")
            .document(rcpe.id).set(data)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }
}

fun readCSV(bufferedReader: BufferedReader): List<Recipe> {
    return bufferedReader.lineSequence()
        .filter { it.isNotBlank() }
        .map {
            val cols = it.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*\$)".toRegex())
            val tags = ArrayList<String>()
            for (tkn in cols[5].split(',', ignoreCase = false)) {
                tags.add(tkn.trim('[', ']', '\'', ' ', '\"'))
            }
            val nutrition = ArrayList<String>()
            for (tkn in cols[6].split(',', ignoreCase = false)) {
                nutrition.add(tkn.trim('[', ']', '\'', ' ', '\"'))
            }
            val steps = ArrayList<String>()
            for (tkn in cols[8].split(',', ignoreCase = false)) {
                steps.add(tkn.trim('[', ']', '\'', ' '))
            }
            val ingredients = ArrayList<String>()
            for (tkn in cols[10].split(',', ignoreCase = false)) {
                ingredients.add(tkn.trim('[', ']', '\'', ' ', '\"'))
            }
            Recipe(
                cols[0],
                cols[1],
                cols[2],
                cols[3],
                cols[4],
                tags,
                nutrition,
                cols[7],
                steps,
                cols[9],
                ingredients,
                cols[11]
            )
        }.toList()
}