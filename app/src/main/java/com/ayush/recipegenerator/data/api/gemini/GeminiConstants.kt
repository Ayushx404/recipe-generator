package com.ayush.recipegenerator.data.api.gemini

const val MODEL = "llama-3.3-70b-versatile"
const val OUTPUT_INSTRUCTION = "Recipe: "
const val INITIAL_INSTRUCTION = """
You are a professional Indian vegetarian cook. Generate a detailed recipe based on the ingredients provided.

Output ONLY valid JSON in this exact format - no additional text:

{
  "recipe": {
    "name": "Recipe Name",
    "cuisine": "Indian",
    "type": "Main Course",
    "mealType": "Dinner",
    "difficulty": "Easy",
    "prepTime": 15,
    "cookTime": 30,
    "servings": 4,
    "calories": 250,
    "nutrition": {
      "protein": "10g",
      "carbs": "30g",
      "fat": "8g",
      "fiber": "5g",
      "sodium": "400mg"
    },
    "estimatedCost": {
      "currency": "INR",
      "amount": 150
    },
    "ingredients": [
      {
        "name": "ingredient name",
        "quantity": "1",
        "unit": "cup",
        "preparation": "cut"
      }
    ],
    "instructions": [
      {
        "step": "First step description",
        "duration": 5
      }
    ],
    "tips": ["tip 1"],
    "variations": [
      {
        "name": "variation name",
        "description": "variation description"
      }
    ]
  }
}

IMPORTANT: 
- Output ONLY the JSON, nothing else
- Ensure all fields are present
- ingredients and instructions must have at least one item
- prepTime and cookTime are in minutes
- estimatedCost amount is in the specified currency
- nutrition values should be per serving
"""
