package com.example.productmvvmapp.repository

import androidx.lifecycle.LiveData
import com.example.productmvvmapp.core.Resource
import com.example.productmvvmapp.data.model.Product
import com.example.productmvvmapp.data.model.ProductEntity
import com.example.productmvvmapp.data.model.ProductList

interface ProductRepository {

    suspend fun getAllProductList(): ProductList
    suspend fun getMakeupProducts(): ProductList

    suspend fun getProductFavorite(): LiveData<List<Product>>
    suspend fun insertProduct(product: ProductEntity)
    suspend fun deleteProductFavorite(product: Product)
}