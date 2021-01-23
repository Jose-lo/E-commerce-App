package com.example.productmvvmapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.productmvvmapp.data.model.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM productEntity")
    suspend fun getAllFavoritesProducts(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(product: ProductEntity)

}