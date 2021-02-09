package com.example.productmvvmapp.data.remote

import com.example.productmvvmapp.data.model.Product
import com.example.productmvvmapp.data.model.ProductList
import com.example.productmvvmapp.repository.WebService

class RemoteDataSource(private val webService: WebService, private val firestoreClass: FirestoreClass) {

   suspend fun getAllProducts(): ProductList {
        return webService.getAllProducts()
    }

    suspend fun getMakeupProducts(): ProductList {
        return webService.getMakeupProducts()
    }
}