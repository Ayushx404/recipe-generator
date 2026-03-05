package com.ayush.recipegenerator.presentation.generate_recipe.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ContextualFlowRowOverflow
import androidx.compose.foundation.layout.ContextualFlowRowOverflowScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ayush.recipegenerator.data.model.CheckableItem
import com.ayush.recipegenerator.data.static.extractMainName
import com.ayush.recipegenerator.domain.usecases.GetStaticIngredient

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Ingredients(
    modifier: Modifier = Modifier,
    ingredientsMap: Map<String, List<CheckableItem>> = mutableMapOf(),
    onSelectionChange: () -> Unit
) {
    // Remember the scroll state for the column
    val scrollState = rememberScrollState()

    // Column containing all ingredients
    Column(
        modifier = modifier.verticalScroll(scrollState)
    ) {
        ingredientsMap.keys.forEach { header ->
            androidx.compose.runtime.key(header) {
                // Display the header text
                Text(
                    text = header,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )

                // Manage the maximum number of lines for the flow row
                var maxLines by remember { mutableIntStateOf(3) }

                val moreOrCollapseIndicator = @Composable { scope: ContextualFlowRowOverflowScope ->
                    // Calculate remaining items and label for expand/collapse indicator
                    val remainingItems = scope.totalItemCount - scope.shownItemCount
                    val label = if (remainingItems == 0) "Less" else "+$remainingItems"
                    SuggestionChip(
                        onClick = {
                            maxLines = if (remainingItems == 0) 2 else maxLines + 5
                        },
                        label = { Text(text = label) },
                        shape = RoundedCornerShape(14.dp),
                    )
                }

                ContextualFlowRow(
                    modifier = Modifier
                        .safeDrawingPadding()
                        .padding(vertical = 8.dp)
                        .wrapContentHeight(align = Alignment.Top),
                    verticalArrangement = Arrangement.Top,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    maxLines = maxLines,
                    overflow = ContextualFlowRowOverflow.expandOrCollapseIndicator(
                        minRowsToShowCollapse = 4,
                        expandIndicator = moreOrCollapseIndicator,
                        collapseIndicator = moreOrCollapseIndicator
                    ),
                    itemCount = ingredientsMap[header]?.size ?: 0
                ) { index ->
                    // Display each ingredient chip
                    ingredientsMap[header]?.getOrNull(index)?.let {
                        IngredientsChip(it, onSelectionChange)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientsChip(checkableItem: CheckableItem, onSelectionChange: () -> Unit) {
    // Remember the selection state for the chip
    var isSelected by remember { mutableStateOf(false)  }
    isSelected = checkableItem.isSelected

    // Display the filter chip with selection logic
    FilterChip(
        modifier = Modifier.padding(horizontal = 4.dp),
        selected = isSelected,
        shape = RoundedCornerShape(14.dp),
        onClick = {
            isSelected = !isSelected
            checkableItem.isSelected = isSelected
            onSelectionChange()
        },
        label = { Text(text = extractMainName(checkableItem.title)) },
    )
}

@Preview(showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun IngredientsPreview() {
    val getStaticIngredient = GetStaticIngredient().invoke()
    Ingredients(modifier = Modifier.fillMaxSize(), ingredientsMap = getStaticIngredient, onSelectionChange = {})
}
