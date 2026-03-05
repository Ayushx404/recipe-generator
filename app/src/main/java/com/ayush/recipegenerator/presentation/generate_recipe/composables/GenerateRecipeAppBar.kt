package com.ayush.recipegenerator.presentation.generate_recipe.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.ayush.recipegenerator.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateRecipeAppBar(
    onCloseIconClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = R.string.choose_your_ingredients), style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
        actions =  {
            IconButton(onClick = onCloseIconClick) {
                Icon(imageVector = Icons.Rounded.Close, contentDescription = "Close")
            }
        }
    )
}

@Preview(showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun GenerateRecipeAppBarPreview() {
    GenerateRecipeAppBar({})
}
