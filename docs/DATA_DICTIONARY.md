# Recipe Generator - Data Dictionary

## Overview

This document provides a comprehensive overview of all data models used in the Recipe Generator Android application. It describes the structure, fields, and relationships between different models across the data layer.

---

## Table of Contents

1. [Domain Models](#1-domain-models)
2. [API Models](#2-api-models)
3. [Database Entities](#3-database-entities)
4. [UI Models](#4-ui-models)
5. [Static Data](#5-static-data)

---

## 1. Domain Models

### 1.1 Recipe

**File:** `data/model/Recipe.kt`

Represents the main domain model for a recipe in the application.

| Field | Type | Default | Description |
|-------|------|---------|-------------|
| `id` | Int | -1 | Unique identifier for the recipe. Defaults to -1 if not specified. |
| `apiRecipe` | ApiRecipe | ApiRecipe.INVALID_API_RECIPE | The API representation containing all recipe details. |
| `generatedAt` | String | "" | Timestamp when the recipe was generated (ISO format). |
| `isSaved` | Int | 0 | Save status flag (0 = not saved, 1 = saved). |

**Companion Object:**
- `INVALID_RECIPE`: Returns a default Recipe instance with id = -1
- `toRecipeEntity()`: Extension function to convert Recipe to RecipeEntity

---

## 2. API Models

### 2.1 ApiRecipe

**File:** `data/api/model/ApiRecipe.kt`

The primary API response model representing a complete recipe with all its details.

| Field | Type | Default | Description |
|-------|------|---------|-------------|
| `name` | String? | "" | Name of the recipe. |
| `cuisine` | String? | "" | Cuisine type (e.g., Indian, Italian). |
| `type` | String? | "" | Recipe type (e.g., Vegetarian, Non-veg). |
| `mealType` | String? | "" | Meal category (e.g., Breakfast, Lunch, Dinner). |
| `difficulty` | String? | "" | Difficulty level (Easy, Medium, Hard). |
| `prepTime` | Int? | 0 | Preparation time in minutes. |
| `cookTime` | Int? | 0 | Cooking time in minutes. |
| `servings` | Int? | 0 | Number of servings. |
| `calories` | Int? | 0 | Calorie count per serving. |
| `nutrition` | ApiNutrition? | null | Nutritional information. |
| `estimatedCost` | ApiEstimatedCost? | null | Estimated cost of ingredients. |
| `details` | String? | "" | Detailed description of the recipe. |
| `apiIngredients` | List<ApiIngredient> | emptyList() | List of ingredients required. |
| `apiInstructions` | List<ApiInstruction> | emptyList() | Step-by-step cooking instructions. |
| `tips` | List<String> | emptyList() | Helpful tips for the recipe. |
| `apiVariations` | List<ApiVariation> | emptyList() | Alternative versions of the recipe. |

**Companion Object:**
- `INVALID_API_RECIPE`: Returns a default empty ApiRecipe instance

---

### 2.2 ApiIngredient

**File:** `data/api/model/ApiIngredient.kt`

Represents an ingredient used in a recipe.

| Field | Type | Default | Description |
|-------|------|---------|-------------|
| `image_url` | String | "" | URL to ingredient image (JSON ignored). |
| `name` | String | "" | Name of the ingredient. |
| `preparation` | String | "" | Preparation method (e.g., chopped, diced, sliced). |
| `quantity` | String | "" | Amount required (e.g., "1", "2", "1/2"). |
| `unit` | String | "" | Unit of measurement (e.g., cup, tablespoon, grams). |

---

### 2.3 ApiInstruction

**File:** `data/api/model/ApiInstruction.kt`

Represents a single cooking step.

| Field | Type | Default | Description |
|-------|------|---------|-------------|
| `step` | String | "" | Text description of the cooking step. |
| `duration` | Int? | 0 | Time required for this step in minutes. |

---

### 2.4 ApiVariation

**File:** `data/api/model/ApiVariation.kt`

Represents an alternative version or variation of a recipe.

| Field | Type | Default | Description |
|-------|------|---------|-------------|
| `name` | String | "" | Name/title of the variation. |
| `description` | String | "" | Description of what makes this variation unique. |

---

### 2.5 ApiNutrition

**File:** `data/api/model/ApiRecipe.kt`

Nutritional information for a recipe.

| Field | Type | Default | Description |
|-------|------|---------|-------------|
| `protein` | String? | "" | Protein content. |
| `carbs` | String? | "" | Carbohydrate content. |
| `fat` | String? | "" | Fat content. |
| `fiber` | String? | "" | Fiber content. |
| `sodium` | String? | "" | Sodium content. |

---

### 2.6 ApiEstimatedCost

**File:** `data/api/model/ApiRecipe.kt`

Estimated cost information for a recipe.

| Field | Type | Default | Description |
|-------|------|---------|-------------|
| `currency` | String? | "INR" | Currency code (default: Indian Rupee). |
| `amount` | Int? | 0 | Estimated cost amount. |

---

### 2.7 ApiRecipeMain

**File:** `data/api/model/ApiRecipeMain.kt`

Root wrapper for API response.

| Field | Type | Default | Description |
|-------|------|---------|-------------|
| `recipe` | ApiRecipe | - | The main recipe object. |

---

## 3. Database Entities

### 3.1 RecipeEntity

**File:** `data/db/entities/RecipeEntity.kt`

Room database entity for persisting recipes locally.

| Field | Type | Description |
|-------|------|-------------|
| `id` | Int (Primary Key, Auto-generated) | Unique identifier, auto-generated by Room. |
| `apiRecipe` | ApiRecipe | Full recipe data stored as JSON (via TypeConverter). |
| `generatedAt` | String | Timestamp of recipe generation. |
| `isSaved` | Int | Save status (0 = not saved, 1 = saved). |

**Table Name:** `recipe`

**Type Converters:**
- `RecipeConverter`: Converts ApiRecipe to/from JSON for database storage

**Extension Function:**
- `toRecipeUIModel()`: Converts RecipeEntity to Recipe domain model

---

## 4. UI Models

### 4.1 CheckableItem

**File:** `data/model/CheckableItem.kt`

Represents a selectable ingredient item in the UI for ingredient selection screen.

| Field | Type | Default | Description |
|-------|------|---------|-------------|
| `isSelected` | Boolean | false | Selection state (checked/unchecked). |
| `title` | String | - | Display name of the ingredient. |

**Usage:**
- Used in ingredient selection chips on Generate Recipe screen
- Modified directly for selection state management

---

## 5. Static Data

### 5.1 Ingredient Categories

**File:** `data/static/Ingredients.kt`

Pre-defined ingredient lists organized by category.

#### Vegetables
| Ingredient Name | Hindi/Regional Name |
|-----------------|---------------------|
| Potato | Aloo |
| Onion | Pyaz |
| Tomato | Tamatar |
| Brinjal/Eggplant | Baingan |
| Bottle Gourd | Lauki |
| Okra | Bhindi |
| Bitter Gourd | Karela |
| Ridge Gourd | Torai |
| French Beans | Beans/French Beans |
| Spinach | Palak |
| Fenugreek Leaves | Methi |
| Coriander Leaves | Dhaniya |
| Cauliflower | Gobi |
| Green Chillies | Mirchi |
| Drumstick | Sahjan |
| Pumpkin | Kaddu |
| Cluster Beans | Guar Phali |
| Snake Gourd | Parwal |
| Indian Bean | Lobia |
| Green Peas | Matar |
| Bottle Gourd Flower | Lauki Phool |
| Mushroom | Khumb |
| Asparagus | Shevaga |
| Beetroot | Chukandar |
| Broccoli | Brokoli |
| Carrots | Gajar |
| Cucumber | Kheera |
| Drumstick Leaves | Sahjan ke Patte |
| Snow Peas | Snap Peas |

#### Spices
| Spice Name | Hindi/Regional Name |
|------------|---------------------|
| Red Chili Powder | Lal Mirchi Powder |
| Cumin Seeds | Jeera |
| Coriander Powder | Dhania Powder |
| Turmeric Powder | Haldi |
| Black Mustard Seeds | Rai |
| Fennel Seeds | Saunf |
| Cardamom | Elaichi |
| Cinnamon | Dalchini |
| Cloves | Lavang |
| Garam Masala | Garam Masala Powder |
| Fenugreek Seeds | Methi Dana |
| Black Peppercorns | Kali Mirch |
| Bay Leaves | Tej Patta |
| Caraway Seeds | Shahi Jeera |
| Star Anise | Chakra Phool |
| Nigella Seeds | Kalonji |
| Mace | Javitri |
| Nutmeg | Jaiphal |
| Ajwain | Carom Seeds |
| Deggi Mirch | Long Red Chillies |

#### Cooking Oils
| Oil Name | Hindi/Regional Name |
|----------|---------------------|
| Mustard Oil | Sarson ka Tel |
| Sunflower Oil | Surajmukhi ka Tel |
| Groundnut Oil | Mungfali ka Tel |
| Coconut Oil | Nariyal ka Tel |
| Palm Oil | Palm Tel |
| Soybean Oil | Soyabean ka Tel |
| Rice Bran Oil | Chawal ki Chot |
| Ghee | Clarified Butter |
| Cottonseed Oil | Kapas ke Beej ka Tel |
| Refined Sunflower Oil | Surajmukhi ka Tel |
| Olive Oil | Zaitoon ka Tel |
| Avocado Oil | Avocardo ka Tel |
| Sesame Oil | Til ka Tel |
| Canola Oil | Kanoda ka Tel |
| Castor Oil | Erand ka Tel |
| Almond Oil | Badam ka Tel |
| Flaxseed Oil | Alsi ka Tel |
| Walnut Oil | Akhrot ka Tel |
| Grapeseed Oil | Angoor ke Beej ka Tel |
| Peanut Oil | Mungfali ka Tel |

#### Dairy Products
| Product Name | Hindi/Regional Name |
|--------------|---------------------|
| Ghee | Clarified Butter |
| Curd | Dahi |
| Paneer | Indian Cottage Cheese |
| Butter | Makhan |
| Yogurt | Dahi |
| Milk | Doodh |
| Fresh Cream | Malai |
| Khoya | Mawa |
| Buttermilk | Chaas |
| Cheese | Cheese |

---

## Data Flow

```
API Response (JSON)
       │
       ▼
┌──────────────────┐
│   ApiRecipe      │  (API Model)
└────────┬─────────┘
         │
         │ toRecipeEntity()
         ▼
┌──────────────────┐
│  RecipeEntity    │  (Database Entity)
└────────┬─────────┘
         │
         │ toRecipeUIModel()
         ▼
┌──────────────────┐
│     Recipe       │  (Domain Model)
└────────┬─────────┘
         │
         ▼
┌──────────────────┐
│   UI Composables │  (Presentation Layer)
└──────────────────┘
```

---

## State Classes

### GenerateRecipeState

**File:** `presentation/generate_recipe/GenerateRecipeState.kt`

| Field | Type | Default | Description |
|-------|------|---------|-------------|
| `isLoading` | Boolean | false | Indicates if recipe generation is in progress. |
| `generatedRecipe` | Recipe | Recipe.INVALID_RECIPE | The generated recipe object. |
| `isErrorMessage` | String | "" | Error message if generation fails. |

---

## Helper Functions

### extractMainName

**File:** `data/static/Ingredients.kt`

Extracts the primary name from an ingredient string (removes Hindi/regional name in parentheses).

**Example:**
```
Input:  "Potato (Aloo)"
Output: "Potato"
```

---

## Version History

| Version | Date | Changes |
|---------|------|---------|
| 1.0 | Initial | Initial data dictionary creation |

---

*Document generated for Recipe Generator Android Application*
