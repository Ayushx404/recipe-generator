package com.ayush.recipegenerator.presentation.common

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants.IterateForever
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ayush.recipegenerator.R

/**
 * Composable function to display a screen when no recipes are found.
 *
 * @param modifier Modifier for adjusting the layout and appearance of the entire screen.
 * @param showAddExpenseButton Flag to determine whether to show the "Generate Recipe" button.
 * @param onGenerateRecipeButtonClick Callback function triggered when the "Generate Recipe" button is clicked.
 */
@Composable
fun NoRecipeFound(
    modifier: Modifier = Modifier,
    @StringRes messageId: Int= 0,
    showAddExpenseButton: Boolean = true,
    onGenerateRecipeButtonClick: () -> Unit,
) {
    // Load Lottie animation for no record found
    val noRecordFoundComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.no_record_found))

    // Animate the Lottie composition indefinitely
    val progress by animateLottieCompositionAsState(
        composition = noRecordFoundComposition,
        iterations = IterateForever
    )

    // Column layout to vertically stack components in center
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Lottie animation view
        LottieAnimation(
            composition = noRecordFoundComposition,
            progress = progress
        )

        // Text message indicating no recipes found
        Text(
            modifier = Modifier.padding(horizontal = 30.dp),
            text = stringResource(id = if(messageId == 0)  R.string.no_recipe_found else messageId),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        // Spacer to add a small gap between text and button
        Spacer(modifier = Modifier.height(4.dp))

        // Button to generate a new recipe (optional)
        if (showAddExpenseButton) {
            Button(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 16.dp),
                onClick = onGenerateRecipeButtonClick,
            ) {
                Row(
                    modifier = Modifier
                        .padding(end = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Icon for the button
                    Image(
                        modifier = Modifier
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_magic_wand),
                        contentDescription = "Generate",
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onTertiary)
                    )

                    // Text label for the button
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.generate_recipe),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight(600)
                        )
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
private fun NoExpenseFoundPreview() {
    NoRecipeFound {
        // Preview placeholder for button click action
    }
}