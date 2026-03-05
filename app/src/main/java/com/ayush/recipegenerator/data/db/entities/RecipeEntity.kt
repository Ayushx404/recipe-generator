package com.ayush.recipegenerator.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ayush.recipegenerator.data.api.model.ApiRecipe
import com.ayush.recipegenerator.data.db.converters.RecipeConverter
import com.ayush.recipegenerator.data.model.Recipe

// Defines a table named "recipe" in the database with columns for id, recipe, and generatedAt
@Entity(tableName = "recipe")
@TypeConverters(RecipeConverter::class) // Specifies the converter for handling Recipe objects
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    // Column to store the Recipe object, using the RecipeConverter to convert to/from JSON
    val apiRecipe: ApiRecipe,
    // Column to store the timestamp of when the recipe was generated
    @ColumnInfo(name = "generate_at")
    val generatedAt: String,

    @ColumnInfo(name = "is_saved")
    val isSaved: Int
)

fun RecipeEntity.toRecipeUIModel(): Recipe {
    return Recipe(id, apiRecipe, generatedAt,isSaved)
}
