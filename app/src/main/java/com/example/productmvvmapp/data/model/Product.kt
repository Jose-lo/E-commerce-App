package com.example.productmvvmapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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

@Entity(tableName = "productEntity")
data class ProductEntity (

        @PrimaryKey
        val id: Int= 0,
        @ColumnInfo(name = "name")
        val name: String= "",
        @ColumnInfo(name = "description")
        val description: String = "",
        @ColumnInfo(name = "miniRating")
        val miniRating: Double = 0.0,
        @ColumnInfo(name = "totalRating")
        val totalRating: Int = 0,
        @ColumnInfo(name = "price")
        val price: Double = 0.0,
        @ColumnInfo(name = "cuttedPrec")
        val cuttedPrec: Double = 0.0,
        @ColumnInfo(name = "descriptionLarge")
        val descriptionLarge: String = "",
        @ColumnInfo(name = "image")
        val image: String = ""
)

fun Product.toProductEntity(): ProductEntity = ProductEntity(
    this.id,
    this.name,
    this.description,
    this.miniRating,
    this.totalRating,
    this.price,
    this.cuttedPrec,
    this.descriptionLarge,
    this.image
)

fun List<ProductEntity>.asProductList(): List<Product> = this.map {
        Product(
                it.id,
                it.name,
                it.description,
                it.miniRating,
                it.totalRating,
                it.price,
                it.cuttedPrec,
                it.descriptionLarge,
                it.image
        )
}