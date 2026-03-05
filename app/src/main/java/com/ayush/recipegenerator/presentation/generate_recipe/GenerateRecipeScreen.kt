package com.ayush.recipegenerator.presentation.generate_recipe

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ayush.recipegenerator.R
import com.ayush.recipegenerator.data.model.CheckableItem
import com.ayush.recipegenerator.data.model.Recipe
import com.ayush.recipegenerator.domain.usecases.GetStaticIngredient
import com.ayush.recipegenerator.presentation.common.Loader
import com.ayush.recipegenerator.presentation.generate_recipe.composables.GenerateRecipeAppBar
import com.ayush.recipegenerator.presentation.generate_recipe.composables.Ingredients

/**
 * Composable function for the Generate Recipe screen.
 *
 * @param modifier Modifier for adjusting the layout and appearance of the screen.
 * @param recipeViewModel ViewModel to handle business logic related to generating recipes.
 * @param navigateToRecipeDetailScreen Callback to handle navigation from GenerateRecipeScreen to RecipeDetailScreen.
 * @param onPop Callback function to handle navigation back from the screen.
 */
@Composable
fun GenerateRecipeScreen(
    modifier: Modifier = Modifier,
    recipeViewModel: GenerateRecipeViewModel = hiltViewModel(),
    navigateToRecipeDetailScreen: (Int) -> Unit,
    onPop: () -> Unit
) {
    // Obtain static ingredients from the view model
    val staticIngredient by remember {
        recipeViewModel.staticIngredients
    }

    // Obtain custom ingredients from the view model
    val customIngredient by remember {
        recipeViewModel.customIngredients
    }

    // Render the GenerateRecipeScaffold passing necessary parameters
    GenerateRecipeScaffold(
        modifier,
        staticIngredient,
        customIngredient,
        recipeViewModel,
        navigateToRecipeDetailScreen = navigateToRecipeDetailScreen,
        onPop = onPop
    )
}


/**
 * Private composable function for the scaffold of the Generate Recipe screen.
 *
 * @param modifier Modifier for adjusting the layout and appearance of the scaffold.
 * @param staticIngredientsMap Map of static ingredients to be displayed.
 * @param customIngredientsMap Map of custom ingredients added by user.
 * @param viewModel The ViewModel instance.
 * @param navigateToRecipeDetailScreen Callback to handle navigation from GenerateRecipeScreen to RecipeDetailScreen.
 * @param onPop Callback function to handle navigation back from the screen.
 */
@Composable
private fun GenerateRecipeScaffold(
    modifier: Modifier = Modifier,
    staticIngredientsMap: Map<String, List<CheckableItem>>,
    customIngredientsMap: Map<String, List<CheckableItem>>,
    viewModel: GenerateRecipeViewModel,
    navigateToRecipeDetailScreen: (Int) -> Unit,
    onPop: () -> Unit
) {
    // Combine static and custom ingredients for display
    val allIngredients = remember(staticIngredientsMap, customIngredientsMap) {
        (staticIngredientsMap.toMutableMap()).apply {
            putAll(customIngredientsMap)
        }
    }

    // Observe the state related to recipe generation
    val generateRecipeState by remember {
        viewModel.generateRecipeState
    }

    // State for showing the add ingredient dialog
    var showAddIngredientDialog by remember {
        mutableStateOf(false)
    }

    // Scaffold provides basic material design structure with a top app bar
    Scaffold(
        topBar = {
            // Custom app bar for the Generate Recipe screen
            GenerateRecipeAppBar(onCloseIconClick = onPop)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddIngredientDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.add_ingredient)
                )
            }
        }
    ) { paddingValues ->

        var isAnyItemSelected by remember {
            mutableStateOf(false)
        }

        // Box composable for managing loading state or displaying content
        Box {
            // Column composable to arrange UI elements vertically with padding
            Column(modifier = Modifier.padding(paddingValues)) {

                // Add Ingredient button at the top
                TextButton(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                        .fillMaxWidth(),
                    onClick = { showAddIngredientDialog = true }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = stringResource(id = R.string.add_ingredient),
                            color = MaterialTheme.colorScheme.primary,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = TextUnit(16f, TextUnitType.Sp)
                            )
                        )
                    }
                }

                // Display the list of ingredients with checkboxes
                Ingredients(
                    modifier = modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    ingredientsMap = allIngredients,
                    onSelectionChange = {
                        isAnyItemSelected =
                            allIngredients.values.flatten().any { it.isSelected }
                    })


                AnimatedVisibility(visible = isAnyItemSelected) {
                    // Column to arrange the button vertically within the AnimatedVisibility composable
                    Column {
                        // TextButton to initiate the recipe generation process
                        TextButton(
                            modifier = Modifier
                                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                                .fillMaxWidth(),
                            onClick = {
                                viewModel.clearAllSelection()
                                isAnyItemSelected = false
                            }
                        ) {
                            // Row to arrange the icon and text horizontally within the button
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Icon to represent the clear all action
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = stringResource(id = R.string.clear_all),
                                    tint = Color.Red
                                )
                                // Text to accompany the icon
                                Text(
                                    text = stringResource(id = R.string.clear_all),
                                    color = Color.Red,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = TextUnit(16f, TextUnitType.Sp)
                                    )
                                )
                            }
                        }

                        // Button to initiate recipe generation process
                        Button(
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                                .fillMaxWidth(),
                            onClick = viewModel::generateRecipe
                        ) {
                            Text(text = stringResource(id = R.string.generate_recipe))
                        }
                    }
                }
            }


            if (generateRecipeState.isLoading) {
                // Show loading indicator if recipe is being generated
                Loader()
            } else if (generateRecipeState.isErrorMessage.isNotEmpty() && generateRecipeState.generatedRecipe == Recipe.INVALID_RECIPE) {
                Toast.makeText(
                    LocalContext.current,
                    generateRecipeState.isErrorMessage,
                    Toast.LENGTH_LONG
                ).show()
            } else if (generateRecipeState.generatedRecipe != Recipe.INVALID_RECIPE) {
                // navigate to RecipeDetailScreen on successful generation of Recipe
                navigateToRecipeDetailScreen(generateRecipeState.generatedRecipe.id)
                viewModel.resetRecipeDetail()
            }

        }
    }

    // Add Ingredient Dialog
    if (showAddIngredientDialog) {
        AddIngredientDialog(
            onDismiss = { showAddIngredientDialog = false },
            onAdd = { name, category ->
                viewModel.addCustomIngredient(name, category)
                showAddIngredientDialog = false
            }
        )
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun GenerateRecipePreview() {
    val staticIngredient = GetStaticIngredient().invoke()
    GenerateRecipeScaffold(
        staticIngredientsMap = staticIngredient,
        customIngredientsMap = emptyMap(),
        viewModel = hiltViewModel(),
        navigateToRecipeDetailScreen = {},
        onPop = {})
}

@Composable
private fun AddIngredientDialog(
    onDismiss: () -> Unit,
    onAdd: (name: String, category: String?) -> Unit
) {
    var ingredientName by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    val categories = listOf("Vegetables", "Spices", "Cooking Oil", "Dairy Products")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(id = R.string.add_ingredient))
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = ingredientName,
                    onValueChange = { ingredientName = it },
                    label = { Text(stringResource(id = R.string.ingredient_name)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Text(
                    text = stringResource(id = R.string.select_category),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

                Column(modifier = Modifier.selectableGroup()) {
                    categories.forEach { category ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = selectedCategory == category,
                                    onClick = { selectedCategory = category },
                                    role = Role.RadioButton
                                )
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedCategory == category,
                                onClick = null
                            )
                            Text(
                                text = category,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = selectedCategory == null,
                                onClick = { selectedCategory = null },
                                role = Role.RadioButton
                            )
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedCategory == null,
                            onClick = null
                        )
                        Text(
                            text = stringResource(id = R.string.standalone),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (ingredientName.isNotBlank()) {
                        onAdd(ingredientName.trim(), selectedCategory)
                    }
                },
                enabled = ingredientName.isNotBlank()
            ) {
                Text(stringResource(id = R.string.add))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    )
}
