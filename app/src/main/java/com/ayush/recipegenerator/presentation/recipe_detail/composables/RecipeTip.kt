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

/**
 * A Composable function to display a list of recipe tips.
 *
 * @param modifier A [Modifier] for styling and layout purposes.
 * @param tips A list of [String] objects representing the tips for the recipe.
 */
@Composable
fun RecipeTip(
    modifier: Modifier = Modifier,
    tips: List<String>
) {
    Column(modifier = modifier.padding(16.dp)) {
        // Display the "Tips" header text
        Text(
            text = stringResource(id = R.string.tips),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        // Add spacing between the header and the tips list
        Spacer(modifier = Modifier.height(6.dp))
        // Display the list of tips
        Tip(tips = tips)
    }
}

/**
 * A Composable function that displays each tip in the list.
 *
 * @param tips A list of [String] objects representing the tips for the recipe.
 */
@Composable
fun Tip(
    tips: List<String>
) {
    // Iterate through each tip
    tips.forEach { tip ->
        Row(
            verticalAlignment = Alignment.Top,
        ) {
            // Create a circular bullet point for each tip
            Box(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.onBackground)
            )
            Spacer(modifier = Modifier.width(20.dp))
            // Display the tip text
            Text(text = tip, modifier = Modifier.padding(bottom = 10.dp)
            )
        }
    }
}

/**
 * A Composable function for previewing the [RecipeTip] composable.
 *
 * This function is used only in the Android Studio Preview and not in production.
 */
@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
private fun RecipeTipPreview() {
    // Sample list of tips for preview purposes
    val previewTips = listOf(
        "You can add a pinch of garam masala for extra flavor.",
        "Use fresh ingredients for the best taste.",
        "Let the dish rest for a few minutes before serving."
    )
    // Display the RecipeTip composable with the sample data
    RecipeTip(tips = previewTips)
}