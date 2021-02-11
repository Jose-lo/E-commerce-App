package com.example.productmvvmapp.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.productmvvmapp.core.Resource
import com.example.productmvvmapp.data.model.*

class LocalDataSource(private val productDao: ProductDao) {

    //Favorites

    suspend fun saveProduct(product: ProductEntity){
        productDao.insertFavorite(product)
    }

    fun getProductFavorites():LiveData<List<Product>>{
        return productDao.getAllFavoritesProducts().map { it.asProductList() }
    }

    suspend fun deleteProductFavorite(product: Product){
        productDao.deleteFavorite(product.toProductEntity())
    }

    //Car

    fun getCarProducts():LiveData<List<Product>>{
        return productDao.getCartProducts().map { it.asProducCartList() }
    }

    suspend fun insertCar(product: Product){
        productDao.insertCart(product.toProductCartEntity())
    }

    suspend fun deleteCar(product: Product){
        productDao.deleteCart(product.toProductCartEntity())
    }

    suspend fun isCartInsert(product: Product): Boolean{
        return productDao.getCarById(product.id.toString()) != null
    }

}