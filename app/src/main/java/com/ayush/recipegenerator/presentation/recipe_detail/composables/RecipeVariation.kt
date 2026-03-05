package com.ayush.recipegenerator.presentation.recipe_detail.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ayush.recipegenerator.R
import com.ayush.recipegenerator.data.api.model.ApiVariation

/**
 * A Composable function to display a list of recipe variations.
 *
 * @param modifier A [Modifier] for styling and layout purposes.
 * @param variations A list of [ApiVariation] objects representing the variations of the recipe.
 */
@Composable
fun RecipeVariation(
    modifier: Modifier = Modifier,
    variations: List<ApiVariation>
) {
    Column(modifier = modifier.padding(16.dp)) {
        // Display the "Variations" header text
        Text(
            text = stringResource(id = R.string.variations),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        // Add spacing between the header and the variations list
        Spacer(modifier = Modifier.height(6.dp))
        // Display the list of variations
        Variation(variations = variations)
    }
}

/**
 * A Composable function that displays each variation in the list.
 *
 * @param variations A list of [ApiVariation] objects representing the variations of the recipe.
 */
@Composable
fun Variation(
    variations: List<ApiVariation>
) {
    // Iterate through each variation
    variations.forEach { variation ->
        Row(
            verticalAlignment = Alignment.Top,
        ) {
            // Create a circular bullet point for each variation
            Box(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.onBackground)
            )
            Spacer(modifier = Modifier.width(20.dp))
            // Create a formatted string for each variation
            val variationString = "${variation.name} : ${variation.description}"
            // Display the variation string
            Text(text = variationString, modifier = Modifier.padding(bottom = 10.dp)
            )
        }
    }
}

/**
 * A Composable function for previewing the [RecipeVariation] composable.
 *
 * This function is used only in the Android Studio Preview and not in production.
 */
@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
private fun RecipeVariationPreview() {
    // Sample list of variations for preview purposes
    val previewVariations = listOf(
        ApiVariation(
            name = "Spicy Aloo Dahi",
            description = "For a spicier version, add green chilies to the gravy."
        ),
        ApiVariation(
            name = "Vegan Aloo Dahi",
            description = "To make it a vegan dish, use a plant-based yogurt alternative."
        )
    )
    // Display the RecipeVariation composable with the sample data
    RecipeVariation(variations = previewVariations)
}