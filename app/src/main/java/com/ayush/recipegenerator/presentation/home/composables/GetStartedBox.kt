package com.ayush.recipegenerator.presentation.home.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayush.recipegenerator.R

/**
 * Composable function representing a card with an image, text, and a button to get started.
 *
 * @param modifier Modifier for adjusting the layout and appearance of the card.
 * @param onGetStartedButtonClick Callback function triggered when the "Get Started" button is clicked.
 */
@Composable
fun GetStartedBox(
    modifier: Modifier = Modifier,
    onGetStartedButtonClick: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Row containing an image and text
        Row {
            Image(
                modifier = Modifier
                    .padding(12.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
                    .padding(8.dp)
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_magic_wand),
                contentDescription = "Generate",
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primaryContainer)
            )

            Text(
                modifier = Modifier.padding(top = 14.dp, end = 12.dp),
                text = stringResource(id = R.string.recipe_from_your_ingredients),
                style = MaterialTheme.typography.labelLarge
            )
        }

        // Button with text and icon
        Button(
            modifier = modifier
                .padding(horizontal = 12.dp, vertical = 16.dp)
                .fillMaxWidth(),
            onClick = onGetStartedButtonClick,
        ) {
            Row(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_magic_wand),
                    contentDescription = "Generate",
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onTertiary)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.get_started),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 16.sp ,
                        fontWeight = FontWeight(600)
                    )
                )
            }
        }
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
private fun GetStartedBoxPreview() {
    GetStartedBox(onGetStartedButtonClick = {})
}