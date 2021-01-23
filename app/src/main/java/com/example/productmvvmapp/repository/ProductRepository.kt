package com.example.productmvvmapp.repository

import com.example.productmvvmapp.core.Resource
import com.example.productmvvmapp.data.model.ProductEntity
import com.example.productmvvmapp.data.model.ProductList

interface ProductRepository {

    suspend fun getAllProductList(): ProductList
    suspend fun getMakeupProducts(): ProductList

    suspend fun getProductFavorite(): Resource<List<ProductEntity>>
    suspend fun insertProduct(product: ProductEntity)
}