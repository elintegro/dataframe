package com.elintegro.onmange

class ProductType {

    String productTypeName
    String productTypeDescription

    static constraints = {
        productTypeName (nullable: false)
        productTypeDescription (nullable: true)
    }
}
