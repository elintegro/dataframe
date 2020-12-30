package com.elintegro.onmange

class Ingredient {

    Product product
    double quantity
    String notes

    static constraints = {
        product (nullable : false)
        quantity(nullable : false)
        notes(nullable: true)
    }
}
