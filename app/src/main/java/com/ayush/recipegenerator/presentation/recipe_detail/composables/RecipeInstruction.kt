package com.ayush.recipegenerator.presentation.recipe_detail.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ayush.recipegenerator.R
import com.ayush.recipegenerator.data.api.model.ApiInstruction

/**
 * A Composable function to display a list of recipe instructions.
 *
 * @param modifier A [Modifier] for styling and layout purposes.
 * @param instructions A list of [ApiInstruction] objects representing the steps of the recipe.
 */
@Composable
fun RecipeInstruction(
    modifier: Modifier = Modifier,
    instructions: List<ApiInstruction>
) {
    Column(modifier = modifier.padding(16.dp)) {
        // Display the "Instructions" header text
        Text(
            text = stringResource(id = R.string.instructions),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        // Add spacing between the header and the instructions list
        Spacer(modifier = Modifier.height(6.dp))
        // Display the list of instructions
        Instruction(instructions = instructions)
    }
}

/**
 * A Composable function that displays each instruction step in the list.
 *
 * @param instructions A list of [ApiInstruction] objects representing the steps of the recipe.
 */
@Composable
fun Instruction(
    instructions: List<ApiInstruction>
) {
    // Iterate through each instruction, including its index
    instructions.forEachIndexed { index, apiInstruction ->

        var textLayoutResult by remember {
            mutableStateOf<TextLayoutResult?>(null)
        }

        var linesUsed by remember {
            mutableIntStateOf(0)
        }

        Row(
            verticalAlignment = Alignment.Top,
        ) {
            // Display the stepper icon for each instruction
            Stepper(
                modifier = Modifier.padding(top = 4.dp),
                isLastItem = index == instructions.size - 1,
                linesUsed = linesUsed
            )
            Spacer(modifier = Modifier.width(20.dp))
            // Display the instruction step text
            Text(
                text = apiInstruction.step,
                modifier = Modifier.padding(vertical = 0.dp),
                onTextLayout = { result ->
                    textLayoutResult = result
                }
            )

            textLayoutResult?.let {
                linesUsed = it.lineCount
            }
        }
    }
}

/**
 * A Composable function to display a stepper icon.
 *
 * @param modifier A [Modifier] for styling and layout purposes.
 * @param isLastItem A Boolean indicating if this is the last item in the list.
 */
@Composable
fun Stepper(
    modifier: Modifier = Modifier,
    isLastItem: Boolean,
    linesUsed: Int
) {
    val height = when(linesUsed) {
        in 1..2 -> 50.dp
        3 -> 70.dp
        in 4..5 -> 90.dp
        else -> 100.dp
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Create a circular indicator for the step
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.onBackground)
        ) {
            // Circle shape for the step indicator
        }

        // Conditionally display a vertical divider if this is not the last item
        if (!isLastItem) {
            VerticalDivider(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .height(height),
                thickness = 1.5.dp
            )
        }
    }
}

/**
 * A Composable function for previewing the [RecipeInstruction] composable.
 *
 * This function is used only in the Android Studio Preview and not in production.
 */
@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
private fun RecipeInstructionPreview() {
    // Sample list of instructions for preview purposes
    val previewInstruction = listOf(
        ApiInstruction(
            step = "Heat oil in a pan and add the chopped onions. Saute until translucent."
        ),
        ApiInstruction(
            step = "Add the cubed potatoes and cook until they are soft and golden brown."
        ),
        ApiInstruction(
            step = "Add the whisked curd, salt, and spices to the pan. Cook until the gravy thickens."
        ),
        ApiInstruction(
            step = "Serve hot with rice or roti."
        ),
    )
    // Display the RecipeInstruction composable with the sample data
    RecipeInstruction(instructions = previewInstruction)
}