package com.elintegro.onmange

class Product {
    String productName;
    String picture; //url to the image
    String productDescription;
    ProductType productType;
    short priceLevel //(1/2/3) - could be enum!


    static constraints = {
        productName (nullable: false)
        picture (nullable: false)
        productDescription (nullable: false)
        productType (nullable: false)
        priceLevel (nullable: true)
    }
}
