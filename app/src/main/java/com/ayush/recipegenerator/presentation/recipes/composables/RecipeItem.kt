package com.ayush.recipegenerator.presentation.recipes.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayush.recipegenerator.R
import com.ayush.recipegenerator.common.util.formatDate
import com.ayush.recipegenerator.data.api.model.ApiRecipe
import com.ayush.recipegenerator.data.model.Recipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeItem(
    modifier: Modifier = Modifier,
    recipe: Recipe,
    onRecipeClicked: (Recipe) -> Unit,
    onRecipeSwiped: (Recipe) -> Unit
) {

    var isSwiped by remember {
        mutableStateOf(false)
    }

    var show by remember {
        mutableStateOf(true)
    }


    val dismissState =
        rememberSwipeToDismissBoxState(
            confirmValueChange = { dismissValue ->
                if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                    isSwiped = true
                    show = false
                    onRecipeSwiped(recipe)
                    true
                } else {
                    isSwiped = false
                    false
                }
            },
        )

    AnimatedVisibility(visible = show, exit = fadeOut(spring()), modifier = modifier) {
        SwipeToDismissBox(
            state = dismissState,
            backgroundContent = { SwipedBackgroundContent(dismissState = dismissState) },
            content = {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                ) {
                    Box {
                        if (recipe.isSaved == 1) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_filled_saved_vector),
                                contentDescription = stringResource(
                                    id = R.string.saved
                                ),
                                modifier = Modifier
                                    .size(30.dp)
                                    .align(Alignment.TopEnd)
                                    .padding(end = 8.dp, bottom = 12.dp),
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                            )
                        }

                        Column(
                            modifier = Modifier.clickable {
                                onRecipeClicked(recipe)
                            },
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start,
                        ) {
                            Row(
                                modifier = Modifier.padding(top = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                val ingredients = recipe.apiRecipe.apiIngredients
                                var ingredientsText = "6 ingredients"
                                if (ingredients.isNotEmpty()) {
                                    ingredientsText = "${ingredients.size} ingredients"
                                }

                                Text(
                                    modifier = Modifier.padding(start = 12.dp),
                                    text = ingredientsText,
                                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Canvas(modifier = Modifier.size(4.dp)) {
                                    val centerX = size.width / 2f
                                    val centerY = size.height / 2f
                                    val radius = size.minDimension / 2f

                                    drawCircle(
                                        color = Color.Gray, // Set the color of the circle
                                        radius = radius,
                                        center = Offset(centerX, centerY)
                                    )
                                }

                                Text(
                                    text = formatDate(recipe.generatedAt),
                                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            recipe.apiRecipe.name?.let {
                                Text(
                                    modifier = Modifier
                                        .padding(
                                            top = 4.dp,
                                            start = 12.dp,
                                            end = 12.dp,
                                            bottom = 12.dp
                                        )
                                        .fillMaxWidth(),
                                    text = it,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )
                            }
                        }
                    }
                }
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipedBackgroundContent(dismissState: SwipeToDismissBoxState) {
    val color by animateColorAsState(
        targetValue =
        when (dismissState.targetValue) {
            SwipeToDismissBoxValue.EndToStart -> Color.Red
            else -> Color.Transparent
        },
        label = "",
    )

    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .shadow(shape = RoundedCornerShape(20.dp), elevation = 2.dp)
            .background(color = color),
    ) {
        Icon(
            modifier =
            Modifier
                .padding(end = 10.dp)
                .align(Alignment.CenterEnd),
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete Recipe",
        )
    }
}


@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
private fun RecipeItemPreview() {
    RecipeItem(recipe = Recipe(
        id = 3,
        apiRecipe = ApiRecipe(
            name = "Vegetable Stir Fry ",
            details = "No Details Found"
        ),
        generatedAt = "2024-06-25T14:00:00Z",
        isSaved = 1
    ), onRecipeClicked = {}) {

    }
}