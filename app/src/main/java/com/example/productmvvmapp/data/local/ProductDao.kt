package com.example.productmvvmapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.productmvvmapp.data.model.CarEntity
import com.example.productmvvmapp.data.model.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM productEntity")
    fun getAllFavoritesProducts(): LiveData<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(product: ProductEntity)

    @Delete
    suspend fun deleteFavorite(product: ProductEntity)

    @Query("SELECT * FROM carTable")
    fun getCartProducts(): LiveData<List<CarEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(product: CarEntity)

    @Delete
    suspend fun deleteCart(product: CarEntity)

    @Query("SELECT * FROM carTable WHERE id = :id")
    suspend fun getCarById(id: String): CarEntity?

}