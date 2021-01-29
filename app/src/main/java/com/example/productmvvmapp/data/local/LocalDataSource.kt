package com.example.productmvvmapp.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.productmvvmapp.core.Resource
import com.example.productmvvmapp.data.model.Product
import com.example.productmvvmapp.data.model.ProductEntity
import com.example.productmvvmapp.data.model.asProductList
import com.example.productmvvmapp.data.model.toProductEntity

class LocalDataSource(private val productDao: ProductDao) {

    suspend fun saveProduct(product: ProductEntity){
        productDao.insertFavorite(product)
    }

    fun getProductFavorites():LiveData<List<Product>>{
        return productDao.getAllFavoritesProducts().map { it.asProductList() }
    }

    suspend fun deleteProductFavorite(product: Product){
        productDao.deleteFavorite(product.toProductEntity())
    }
}