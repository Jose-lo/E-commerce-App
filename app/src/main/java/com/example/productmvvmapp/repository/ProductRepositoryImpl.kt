package com.example.productmvvmapp.repository

import com.example.productmvvmapp.data.model.ProductList
import com.example.productmvvmapp.data.remote.RemoteDataSource

class ProductRepositoryImpl(private val dataSource:RemoteDataSource):ProductRepository
{
    override suspend fun getAllProductList(): ProductList {
        return dataSource.getAllProducts()
    }

    override suspend fun getMakeupProducts(): ProductList {
        return dataSource.getMakeupProducts()
    }


}