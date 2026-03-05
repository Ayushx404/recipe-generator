package com.ayush.recipegenerator.data.api.model

/**
 * Represents a variation or alternative version of a recipe in the context of API communication.
 *
 * @property description The textual description of the variation.
 * @property name The name or title of the variation.
 */
data class ApiVariation(
    val description: String = "",
    val name: String = "",
)
