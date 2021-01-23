package com.example.productmvvmapp.repository

import com.example.productmvvmapp.data.application.Constants
import com.example.productmvvmapp.data.model.ProductList
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface WebService {

    @GET("products/products.json")
    suspend fun getAllProducts(): ProductList

    @GET("products/makeup.json")
    suspend fun getMakeupProducts(): ProductList

}

object RetrofitClient{

    val webservice by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}