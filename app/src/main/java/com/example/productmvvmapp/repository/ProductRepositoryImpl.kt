package com.example.productmvvmapp.repository

import com.example.productmvvmapp.core.Resource
import com.example.productmvvmapp.data.local.LocalDataSource
import com.example.productmvvmapp.data.model.ProductEntity
import com.example.productmvvmapp.data.model.ProductList
import com.example.productmvvmapp.data.remote.RemoteDataSource

class ProductRepositoryImpl(private val dataSource:RemoteDataSource,
                            private val dataSourceLocal: LocalDataSource
):ProductRepository
{
    override suspend fun getAllProductList(): ProductList {
        return dataSource.getAllProducts()
    }

    override suspend fun getMakeupProducts(): ProductList {
        return dataSource.getMakeupProducts()
    }

    override suspend fun getProductFavorite(): Resource<List<ProductEntity>> {
        return dataSourceLocal.getProductFavorites()
    }

    override suspend fun insertProduct(product: ProductEntity) {
        dataSourceLocal.saveProduct(product)
    }
}