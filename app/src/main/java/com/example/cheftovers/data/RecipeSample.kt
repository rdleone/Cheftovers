package com.example.cheftovers.data

/**
 * Dummy recipe for testing
 * @return recipe   Sample recipe
 */
fun recipeSample(): Recipe {
    return Recipe(
        name = "Chicken Noodle Soup",
        total_time = 240,
        steps = listOf("1. Peel and cut two carrots into medallions, medium onion chopped, and slice two ribs of celery.",
            "2. Trim and cut two skinless boneless chicken breasts into cubes.",
            "3. Add the chicken and veggies to a small slow cooker (3 ½ qt.).  Add  32 oz of chicken broth or stock, one bay leaf, ½ teaspoon of salt, ½ teaspoon pepper. one 14 oz can more of broth if you want a lot of liquid in your soup. Cook on low for 4 hours in the crock pot.",
            "4. After the first 3 hours in the crock pot, add 1 ½ cups of dry noodles and let cook for the final hour. If you like a lot of noodles, add another ½ cup of noodles BUT do not do this without adding more broth from the previous instruction. I suggest standard commercial egg noodles. Please check the pasta every 15 minutes until done. If you use non-standard pasta, you should check the pasta more frequently."),
        description = "This simple classic chicken noodle soup can be made in a crock pot or on the stove-top—get a great homemade taste with this Betty Crocker inspired recipe. Bring on the cold weather; I'm ready now.\n" +
                "This is a four to six serving recipe. You can cut in half easily, but you want a few leftovers.",
        ingredients = listOf("2 skinless boneless chicken breasts", "32-48 oz chicken broth",
                "2 carrots", "2 ribs celery", "1 onion - medium", "½ teaspoon salt",
                "½ teaspoon pepper", "1 bay leaf", "1½ cup dry noodles"),
        categories = listOf("Soup"),
        cook_time = 210,
        prep_time = 30,
        images = listOf(),
        rating = 5.0
//        images = listOf(R.drawable.chicken_noodle_soup),
//        isSaved = false
    )
}