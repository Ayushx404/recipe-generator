package com.ayush.recipegenerator.presentation.main

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.ayush.recipegenerator.presentation.generate_recipe.GenerateRecipeScreen
import com.ayush.recipegenerator.presentation.home.HomeScreen
import com.ayush.recipegenerator.presentation.ui.theme.RecipeGeneratorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeGeneratorTheme {
                RecipeGeneratorMain()
            }
        }
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_5, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    RecipeGeneratorTheme {
        RecipeGeneratorMain()
    }
}
