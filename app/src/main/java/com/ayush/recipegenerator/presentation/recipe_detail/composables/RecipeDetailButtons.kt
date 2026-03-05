package com.ayush.recipegenerator.presentation.recipe_detail.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayush.recipegenerator.R
import com.ayush.recipegenerator.presentation.recipe_detail.RecipeDetailViewModel

@Composable
fun RecipeDetailButtons(
    modifier: Modifier = Modifier,
    recipeDetailViewModel: RecipeDetailViewModel? = null,
    recipeId: Int,
    isSaved: Boolean,
    navigateToHomeScreen: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Button(
            onClick = navigateToHomeScreen,
            modifier = if(isSaved) Modifier.weight(1f) else  Modifier.semantics {  },
            colors = ButtonDefaults.buttonColors().copy(containerColor = Color.Gray, contentColor = Color.White)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_home_vector),
                    contentDescription = "Home",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.back_to_home),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(600)
                    )
                )
            }

        }


        Button(onClick = {
            recipeDetailViewModel?.saveRecipe(recipeId, isSaved = isSaved)
        }, modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(id = if (isSaved) R.drawable.ic_filled_saved_vector else R.drawable.ic_save_vector),
                    contentDescription = "Save Recipe",
                    tint = MaterialTheme.colorScheme.onTertiary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = if(isSaved) {
                        R.string.saved
                    } else {
                        R.string.save_recipe
                    }),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(600)
                    )
                )
            }
        }


    }
}


@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
private fun RecipeDetailButton() {
    Box {
        RecipeDetailButtons(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color.White)
                .padding(16.dp), recipeId = 1, isSaved = false, navigateToHomeScreen = {}
        )
    }
}

