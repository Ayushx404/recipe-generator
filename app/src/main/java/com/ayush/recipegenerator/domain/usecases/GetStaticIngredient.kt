package com.ayush.recipegenerator.domain.usecases

import com.ayush.recipegenerator.data.model.CheckableItem
import com.ayush.recipegenerator.data.static.cookingOils
import com.ayush.recipegenerator.data.static.dairyProducts
import com.ayush.recipegenerator.data.static.spices
import com.ayush.recipegenerator.data.static.vegetables

class GetStaticIngredient {
    operator fun invoke() = mutableMapOf(
        "Vegetables" to vegetables.map { vegetable -> CheckableItem(title = vegetable) },
        "Spices" to spices.map { spice -> CheckableItem(title = spice) },
        "Cooking Oil" to cookingOils.map { cookingOil -> CheckableItem(title = cookingOil) },
        "Dairy Products" to dairyProducts.map { dairyProduct -> CheckableItem(title = dairyProduct) },
    )
}