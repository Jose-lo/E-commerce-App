package com.example.productmvvmapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.productmvvmapp.data.model.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM productEntity")
    fun getAllFavoritesProducts(): LiveData<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(product: ProductEntity)

    @Delete
    suspend fun deleteFavorite(product: ProductEntity)

}