package com.example.productmvvmapp.data.model

data class Product (
    val id: Int= 0,
    val name: String= "",
    val description: String = "",
    val miniRating: Double = 0.0,
    val totalRating: Int = 0,
    val price: Double = 0.0,
    val cuttedPrec: Double = 0.0,
    val descriptionLarge: String = "",
    val image: String = ""
        )

data class ProductList(val products: List<Product> = listOf())