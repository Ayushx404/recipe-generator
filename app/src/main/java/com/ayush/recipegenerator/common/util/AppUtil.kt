package com.ayush.recipegenerator.common.util

import com.ayush.recipegenerator.data.api.model.ApiIngredient
import com.ayush.recipegenerator.data.api.model.ApiInstruction
import com.ayush.recipegenerator.data.api.model.ApiRecipe
import com.ayush.recipegenerator.data.api.model.ApiVariation
import com.ayush.recipegenerator.data.model.Recipe
import java.util.Calendar


/**
 * Returns a string indicating the meal by time based on the current system time.
 *
 * This function determines the current hour of the day using the system's current time in milliseconds.
 * It then returns a string representing a meal time (Breakfast, Lunch, Evening Snacks, or Dinner)
 * based on predefined hour ranges.
 *
 * @return A string indicating the meal time corresponding to the current time of day.
 */
fun getMealByTime(): String {
    // Create a Calendar instance and set the current time in milliseconds
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = System.currentTimeMillis()

    // Get the current hour of the day (0-23)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)

    // Return the corresponding meal time based on the hour of the day
    return when(hour) {
        // 6:00 AM to 11:59 AM
        in 6..11 -> "Breakfast"
        // 12:00 PM to 5:59 PM
        in 12..17 -> "Lunch"
        // 6:00 PM to 8:59 PM
        in 18..20 -> "Evening Snacks"
        // 9:00 PM to 5:59 AM
        else -> "Dinner"
    }
}

fun recipes() = listOf(
    Recipe(
        id = 1,
        apiRecipe = ApiRecipe(
            cuisine = "Italian",
            details = "A classic Italian pasta dish",
            apiIngredients = listOf(
                ApiIngredient(image_url = "", name = "Spaghetti", preparation = "Boiled", quantity = "200", unit = "g"),
                ApiIngredient(image_url = "", name = "Tomato Sauce", preparation = "", quantity = "150", unit = "ml"),
                ApiIngredient(image_url = "", name = "Garlic", preparation = "Minced", quantity = "2", unit = "cloves"),
                ApiIngredient(image_url = "", name = "Olive Oil", preparation = "", quantity = "2", unit = "tbsp"),
                ApiIngredient(image_url = "", name = "Parmesan", preparation = "Grated", quantity = "50", unit = "g")
            ),
            apiInstructions = listOf(
                ApiInstruction(step = "Boil the spaghetti."),
                ApiInstruction(step = "Heat the olive oil in a pan and sauté the garlic."),
                ApiInstruction(step = "Add the tomato sauce and cook for 5 minutes."),
                ApiInstruction(step = "Mix the spaghetti with the sauce."),
                ApiInstruction(step = "Serve with grated Parmesan on top.")
            ),
            name = "Spaghetti al Pomodoro",
            tips = listOf("Use fresh tomatoes for a better taste.", "Add basil for extra flavor."),
            type = "Main Course",
            apiVariations = listOf(
                ApiVariation(description = "Use whole grain pasta for a healthier option.", name = "Whole Grain Spaghetti"),
                ApiVariation(description = "Add meatballs for extra protein.", name = "Spaghetti with Meatballs")
            )
        ),
        generatedAt = "2023-06-25",
        isSaved = 1
    ),
    Recipe(
        id = 2,
        apiRecipe = ApiRecipe(
            cuisine = "Mexican",
            details = "A traditional Mexican dish with a blend of spices",
            apiIngredients = listOf(
                ApiIngredient(image_url = "", name = "Chicken", preparation = "Diced", quantity = "500", unit = "g"),
                ApiIngredient(image_url = "", name = "Taco Seasoning", preparation = "", quantity = "1", unit = "packet"),
                ApiIngredient(image_url = "", name = "Tortillas", preparation = "", quantity = "8", unit = "pieces"),
                ApiIngredient(image_url = "", name = "Lettuce", preparation = "Shredded", quantity = "1", unit = "cup"),
                ApiIngredient(image_url = "", name = "Cheddar Cheese", preparation = "Shredded", quantity = "1", unit = "cup")
            ),
            apiInstructions = listOf(
                ApiInstruction(step = "Cook the chicken with taco seasoning."),
                ApiInstruction(step = "Warm the tortillas."),
                ApiInstruction(step = "Assemble the tacos with chicken, lettuce, and cheese."),
                ApiInstruction(step = "Serve immediately.")
            ),
            name = "Chicken Tacos",
            tips = listOf("Add salsa for extra flavor.", "Use corn tortillas for an authentic taste."),
            type = "Main Course",
            apiVariations = listOf(
                ApiVariation(description = "Use beef instead of chicken.", name = "Beef Tacos"),
                ApiVariation(description = "Add guacamole for a creamy texture.", name = "Chicken Tacos with Guacamole")
            )
        ),
        generatedAt = "2023-06-25",
        isSaved = 1
    ),
    // Add 8 more recipes with similar structure and different details
    Recipe(
        id = 3,
        apiRecipe = ApiRecipe(
            cuisine = "Indian",
            details = "A spicy and flavorful Indian curry",
            apiIngredients = listOf(
                ApiIngredient(image_url = "", name = "Chicken", preparation = "Cut into pieces", quantity = "500", unit = "g"),
                ApiIngredient(image_url = "", name = "Curry Powder", preparation = "", quantity = "2", unit = "tbsp"),
                ApiIngredient(image_url = "", name = "Coconut Milk", preparation = "", quantity = "400", unit = "ml"),
                ApiIngredient(image_url = "", name = "Onion", preparation = "Chopped", quantity = "1", unit = "piece"),
                ApiIngredient(image_url = "", name = "Garlic", preparation = "Minced", quantity = "3", unit = "cloves")
            ),
            apiInstructions = listOf(
                ApiInstruction(step = "Sauté the onions and garlic."),
                ApiInstruction(step = "Add chicken and cook until browned."),
                ApiInstruction(step = "Add curry powder and coconut milk."),
                ApiInstruction(step = "Simmer until the chicken is cooked through."),
                ApiInstruction(step = "Serve with rice.")
            ),
            name = "Chicken Curry",
            tips = listOf("Add fresh cilantro for garnish.", "Use homemade curry powder for a richer flavor."),
            type = "Main Course",
            apiVariations = listOf(
                ApiVariation(description = "Use lamb instead of chicken.", name = "Lamb Curry"),
                ApiVariation(description = "Add vegetables for a healthier option.", name = "Vegetable Chicken Curry")
            )
        ),
        generatedAt = "2023-06-25",
        isSaved = 1
    ),
    Recipe(
        id = 4,
        apiRecipe = ApiRecipe(
            cuisine = "American",
            details = "A classic American burger with a juicy beef patty",
            apiIngredients = listOf(
                ApiIngredient(image_url = "", name = "Ground Beef", preparation = "Formed into patties", quantity = "500", unit = "g"),
                ApiIngredient(image_url = "", name = "Burger Buns", preparation = "", quantity = "4", unit = "pieces"),
                ApiIngredient(image_url = "", name = "Cheddar Cheese", preparation = "Sliced", quantity = "4", unit = "slices"),
                ApiIngredient(image_url = "", name = "Lettuce", preparation = "", quantity = "4", unit = "leaves"),
                ApiIngredient(image_url = "", name = "Tomato", preparation = "Sliced", quantity = "1", unit = "piece")
            ),
            apiInstructions = listOf(
                ApiInstruction(step = "Grill the beef patties until cooked to desired doneness."),
                ApiInstruction(step = "Toast the burger buns."),
                ApiInstruction(step = "Assemble the burgers with beef patties, cheese, lettuce, and tomato."),
                ApiInstruction(step = "Serve with fries.")
            ),
            name = "Cheeseburger",
            tips = listOf("Add pickles for extra crunch.", "Use a brioche bun for a richer taste."),
            type = "Main Course",
            apiVariations = listOf(
                ApiVariation(description = "Use turkey instead of beef.", name = "Turkey Burger"),
                ApiVariation(description = "Add bacon for a smoky flavor.", name = "Bacon Cheeseburger")
            )
        ),
        generatedAt = "2023-06-25",
        isSaved = 1
    ),
    Recipe(
        id = 5,
        apiRecipe = ApiRecipe(
            cuisine = "Japanese",
            details = "A light and healthy sushi roll",
            apiIngredients = listOf(
                ApiIngredient(image_url = "", name = "Sushi Rice", preparation = "Cooked", quantity = "300", unit = "g"),
                ApiIngredient(image_url = "", name = "Nori Sheets", preparation = "", quantity = "4", unit = "pieces"),
                ApiIngredient(image_url = "", name = "Cucumber", preparation = "Julienned", quantity = "1", unit = "piece"),
                ApiIngredient(image_url = "", name = "Avocado", preparation = "Sliced", quantity = "1", unit = "piece"),
                ApiIngredient(image_url = "", name = "Crab Sticks", preparation = "", quantity = "8", unit = "pieces")
            ),
            apiInstructions = listOf(
                ApiInstruction(step = "Spread the sushi rice on the nori sheet."),
                ApiInstruction(step = "Place cucumber, avocado, and crab sticks in the center."),
                ApiInstruction(step = "Roll tightly and slice into pieces."),
                ApiInstruction(step = "Serve with soy sauce and wasabi.")
            ),
            name = "California Roll",
            tips = listOf("Use a bamboo mat for easier rolling.", "Keep your hands wet to prevent rice from sticking."),
            type = "Appetizer",
            apiVariations = listOf(
                ApiVariation(description = "Use smoked salmon instead of crab sticks.", name = "Salmon Roll"),
                ApiVariation(description = "Add cream cheese for a richer taste.", name = "Philadelphia Roll")
            )
        ),
        generatedAt = "2023-06-25",
        isSaved = 1
    ),
    Recipe(
        id = 6,
        apiRecipe = ApiRecipe(
            cuisine = "French",
            details = "A classic French dessert with a creamy texture",
            apiIngredients = listOf(
                ApiIngredient(image_url = "", name = "Eggs", preparation = "Separated", quantity = "4", unit = "pieces"),
                ApiIngredient(image_url = "", name = "Sugar", preparation = "", quantity = "100", unit = "g"),
                ApiIngredient(image_url = "", name = "Milk", preparation = "Heated", quantity = "500", unit = "ml"),
                ApiIngredient(image_url = "", name = "Vanilla Extract", preparation = "", quantity = "1", unit = "tsp"),
                ApiIngredient(image_url = "", name = "Caramel Sauce", preparation = "", quantity = "100", unit = "ml")
            ),
            apiInstructions = listOf(
                ApiInstruction(step = "Whisk the egg yolks with sugar until pale."),
                ApiInstruction(step = "Gradually add the hot milk while whisking."),
                ApiInstruction(step = "Add vanilla extract."),
                ApiInstruction(step = "Pour into ramekins and bake in a water bath."),
                ApiInstruction(step = "Chill and serve with caramel sauce.")
            ),
            name = "Crème Caramel",
            tips = listOf("Use a water bath to prevent cracking.", "Chill overnight for the best texture."),
            type = "Dessert",
            apiVariations = listOf(
                ApiVariation(description = "Add a hint of lemon zest for extra flavor.", name = "Lemon Crème Caramel"),
                ApiVariation(description = "Use coconut milk for a dairy-free version.", name = "Coconut Crème Caramel")
            )
        ),
        generatedAt = "2023-06-25",
        isSaved = 1
    ),
    Recipe(
        id = 7,
        apiRecipe = ApiRecipe(
            cuisine = "Chinese",
            details = "A quick and easy stir-fry with a mix of vegetables",
            apiIngredients = listOf(
                ApiIngredient(image_url = "", name = "Broccoli", preparation = "Chopped", quantity = "200", unit = "g"),
                ApiIngredient(image_url = "", name = "Carrots", preparation = "Sliced", quantity = "2", unit = "pieces"),
                ApiIngredient(image_url = "", name = "Bell Peppers", preparation = "Sliced", quantity = "1", unit = "piece"),
                ApiIngredient(image_url = "", name = "Soy Sauce", preparation = "", quantity = "3", unit = "tbsp"),
                ApiIngredient(image_url = "", name = "Garlic", preparation = "Minced", quantity = "2", unit = "cloves")
            ),
            apiInstructions = listOf(
                ApiInstruction(step = "Heat oil in a wok and sauté the garlic."),
                ApiInstruction(step = "Add the vegetables and stir-fry for 5-7 minutes."),
                ApiInstruction(step = "Add soy sauce and cook for another 2 minutes."),
                ApiInstruction(step = "Serve hot with rice or noodles.")
            ),
            name = "Vegetable Stir-Fry",
            tips = listOf("Use high heat for a quick stir-fry.", "Add a splash of sesame oil for extra flavor."),
            type = "Main Course",
            apiVariations = listOf(
                ApiVariation(description = "Add tofu for protein.", name = "Tofu Vegetable Stir-Fry"),
                ApiVariation(description = "Use oyster sauce instead of soy sauce.", name = "Oyster Sauce Stir-Fry")
            )
        ),
        generatedAt = "2023-06-25",
        isSaved = 1
    ),
    Recipe(
        id = 8,
        apiRecipe = ApiRecipe(
            cuisine = "Greek",
            details = "A healthy and refreshing Greek salad",
            apiIngredients = listOf(
                ApiIngredient(image_url = "", name = "Cucumber", preparation = "Chopped", quantity = "1", unit = "piece"),
                ApiIngredient(image_url = "", name = "Tomatoes", preparation = "Chopped", quantity = "2", unit = "pieces"),
                ApiIngredient(image_url = "", name = "Red Onion", preparation = "Sliced", quantity = "1", unit = "piece"),
                ApiIngredient(image_url = "", name = "Feta Cheese", preparation = "Crumbled", quantity = "100", unit = "g"),
                ApiIngredient(image_url = "", name = "Olives", preparation = "", quantity = "50", unit = "g")
            ),
            apiInstructions = listOf(
                ApiInstruction(step = "Combine all the ingredients in a bowl."),
                ApiInstruction(step = "Drizzle with olive oil and lemon juice."),
                ApiInstruction(step = "Toss gently to mix."),
                ApiInstruction(step = "Serve chilled.")
            ),
            name = "Greek Salad",
            tips = listOf("Use Kalamata olives for authentic taste.", "Add a pinch of oregano for extra flavor."),
            type = "Salad",
            apiVariations = listOf(
                ApiVariation(description = "Add grilled chicken for protein.", name = "Chicken Greek Salad"),
                ApiVariation(description = "Use balsamic vinegar instead of lemon juice.", name = "Balsamic Greek Salad")
            )
        ),
        generatedAt = "2023-06-25",
        isSaved = 1
    ),
    Recipe(
        id = 9,
        apiRecipe = ApiRecipe(
            cuisine = "Thai",
            details = "A spicy and tangy Thai soup",
            apiIngredients = listOf(
                ApiIngredient(image_url = "", name = "Chicken", preparation = "Sliced", quantity = "300", unit = "g"),
                ApiIngredient(image_url = "", name = "Lemongrass", preparation = "Bruised", quantity = "2", unit = "stalks"),
                ApiIngredient(image_url = "", name = "Kaffir Lime Leaves", preparation = "", quantity = "5", unit = "pieces"),
                ApiIngredient(image_url = "", name = "Coconut Milk", preparation = "", quantity = "400", unit = "ml"),
                ApiIngredient(image_url = "", name = "Thai Chili", preparation = "Sliced", quantity = "2", unit = "pieces")
            ),
            apiInstructions = listOf(
                ApiInstruction(step = "Boil water and add lemongrass and kaffir lime leaves."),
                ApiInstruction(step = "Add chicken and cook until done."),
                ApiInstruction(step = "Add coconut milk and Thai chili."),
                ApiInstruction(step = "Simmer for 10 minutes."),
                ApiInstruction(step = "Serve hot.")
            ),
            name = "Tom Kha Gai",
            tips = listOf("Use fresh ingredients for the best flavor.", "Adjust the chili to your spice preference."),
            type = "Soup",
            apiVariations = listOf(
                ApiVariation(description = "Use shrimp instead of chicken.", name = "Tom Kha Goong"),
                ApiVariation(description = "Add mushrooms for extra texture.", name = "Tom Kha Het")
            )
        ),
        generatedAt = "2023-06-25",
        isSaved = 1
    ),
    Recipe(
        id = 10,
        apiRecipe = ApiRecipe(
            cuisine = "Spanish",
            details = "A hearty and flavorful Spanish rice dish",
            apiIngredients = listOf(
                ApiIngredient(image_url = "", name = "Rice", preparation = "Uncooked", quantity = "300", unit = "g"),
                ApiIngredient(image_url = "", name = "Chicken", preparation = "Diced", quantity = "300", unit = "g"),
                ApiIngredient(image_url = "", name = "Chorizo", preparation = "Sliced", quantity = "100", unit = "g"),
                ApiIngredient(image_url = "", name = "Bell Peppers", preparation = "Chopped", quantity = "2", unit = "pieces"),
                ApiIngredient(image_url = "", name = "Saffron", preparation = "", quantity = "1", unit = "pinch")
            ),
            apiInstructions = listOf(
                ApiInstruction(step = "Sauté chicken and chorizo."),
                ApiInstruction(step = "Add bell peppers and cook until soft."),
                ApiInstruction(step = "Add rice and saffron."),
                ApiInstruction(step = "Add water and simmer until rice is cooked."),
                ApiInstruction(step = "Serve hot.")
            ),
            name = "Paella",
            tips = listOf("Use seafood for a different variation.", "Add peas for extra color and flavor."),
            type = "Main Course",
            apiVariations = listOf(
                ApiVariation(description = "Use seafood instead of chicken and chorizo.", name = "Seafood Paella"),
                ApiVariation(description = "Add rabbit for a traditional Valencian version.", name = "Valencian Paella")
            )
        ),
        generatedAt = "2023-06-25",
        isSaved = 1
    )
)
