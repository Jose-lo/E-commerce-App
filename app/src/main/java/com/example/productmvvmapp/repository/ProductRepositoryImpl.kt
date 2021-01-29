package com.example.productmvvmapp.repository

import androidx.lifecycle.LiveData
import com.example.productmvvmapp.core.Resource
import com.example.productmvvmapp.data.local.LocalDataSource
import com.example.productmvvmapp.data.model.Product
import com.example.productmvvmapp.data.model.ProductEntity
import com.example.productmvvmapp.data.model.ProductList
import com.example.productmvvmapp.data.remote.RemoteDataSource

class ProductRepositoryImpl(private val dataSource:RemoteDataSource,
                            private val dataSourceLocal: LocalDataSource
):ProductRepository {
    override suspend fun getAllProductList(): ProductList {
        return dataSource.getAllProducts()
    }

    override suspend fun getMakeupProducts(): ProductList {
        return dataSource.getMakeupProducts()
    }

    override suspend fun getProductFavorite(): LiveData<List<Product>> {
        return dataSourceLocal.getProductFavorites()
    }

    override suspend fun insertProduct(product: ProductEntity) {
        dataSourceLocal.saveProduct(product)
    }

    override suspend fun deleteProductFavorite(product: Product) {
       dataSourceLocal.deleteProductFavorite(product)
    }
}