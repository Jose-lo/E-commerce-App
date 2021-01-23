package com.example.productmvvmapp.data.local

import com.example.productmvvmapp.core.Resource
import com.example.productmvvmapp.data.model.ProductEntity

class LocalDataSource(private val productDao: ProductDao) {

    suspend fun saveProduct(product: ProductEntity){
        productDao.insertFavorite(product)
    }

    suspend fun getProductFavorites():Resource<List<ProductEntity>>{
        return Resource.Success(productDao.getAllFavoritesProducts())
    }
}