package com.elintegro.onmange

class Recipe {

    String nameRecipe
    String descriptionRecipe
    int cookingTime

    static 	hasMany = [ingredients:Ingredient]

    static constraints = {
        nameRecipe (nullable: false)
        descriptionRecipe (nullable: false)
        cookingTime (nullable: true)
    }
}
