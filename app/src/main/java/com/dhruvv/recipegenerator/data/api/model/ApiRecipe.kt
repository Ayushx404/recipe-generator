package com.dhruvv.recipegenerator.data.api.model

import com.dhruvv.recipegenerator.common.util.toDate
import com.dhruvv.recipegenerator.data.db.entities.RecipeEntity
import com.google.gson.annotations.SerializedName

data class ApiRecipe(
    val name: String? = "",
    val cuisine: String? = "",
    val type: String? = "",
    val mealType: String? = "",
    val difficulty: String? = "",
    val prepTime: Int? = 0,
    val cookTime: Int? = 0,
    val servings: Int? = 0,
    val calories: Int? = 0,
    val nutrition: ApiNutrition? = null,
    val estimatedCost: ApiEstimatedCost? = null,
    val details: String? = "",
    @SerializedName("ingredients")
    val apiIngredients: List<ApiIngredient> = mutableListOf(),
    @SerializedName("instructions")
    val apiInstructions: List<ApiInstruction> = mutableListOf(),
    val tips: List<String> = mutableListOf(),
    @SerializedName("variations")
    val apiVariations: List<ApiVariation> = mutableListOf(),
) {
    companion object {
        val INVALID_API_RECIPE = ApiRecipe()
    }
}

data class ApiNutrition(
    val protein: String? = "",
    val carbs: String? = "",
    val fat: String? = "",
    val fiber: String? = "",
    val sodium: String? = ""
)

data class ApiEstimatedCost(
    val currency: String? = "INR",
    val amount: Int? = 0
)

fun ApiRecipe.toRecipeEntity(): RecipeEntity {
    return RecipeEntity(
        id = 0,
        apiRecipe = this,
        generatedAt = System.currentTimeMillis().toDate(),
        isSaved = 0
    )
}
