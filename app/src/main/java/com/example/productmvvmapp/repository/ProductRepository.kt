package com.example.productmvvmapp.repository

import com.example.productmvvmapp.data.model.ProductList

interface ProductRepository {

    suspend fun getAllProductList(): ProductList
    suspend fun getMakeupProducts(): ProductList
}