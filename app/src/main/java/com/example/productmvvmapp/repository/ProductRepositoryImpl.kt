package com.example.productmvvmapp.repository

import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.productmvvmapp.data.local.LocalDataSource
import com.example.productmvvmapp.data.model.*
import com.example.productmvvmapp.data.remote.RemoteDataSource

class ProductRepositoryImpl(
    private val dataSource: RemoteDataSource,
    private val dataSourceLocal: LocalDataSource
):ProductRepository {

    override suspend fun getAllProductList(): ProductList {
        return dataSource.getAllProducts()
    }

    override suspend fun getMakeupProducts(): ProductList {
        return dataSource.getMakeupProducts()
    }

    //Favorite
    override suspend fun getProductFavorite(): LiveData<List<Product>> {
        return dataSourceLocal.getProductFavorites()
    }

    override suspend fun insertProduct(product: ProductEntity) {
        dataSourceLocal.saveProduct(product)
    }

    override suspend fun deleteProductFavorite(product: Product) {
       dataSourceLocal.deleteProductFavorite(product)
    }

    //Cart

    override suspend fun getCartProducts(): LiveData<List<Product>> {
        return dataSourceLocal.getCarProducts()
    }

    override suspend fun insertCartFavorite(product: Product) {
        dataSourceLocal.insertCar(product)
    }

    override suspend fun deleteCartFavorite(product: Product) {
        dataSourceLocal.deleteCar(product)
    }

    override suspend fun isCartInsert(product: Product): Boolean =
        dataSourceLocal.isCartInsert(product)

    //Firebase
    override suspend fun setRegister(
        fragment: Fragment,
        email: EditText,
        password: EditText,
        firstName: EditText,
        lastName: EditText
    ) {
        dataSource.setRegister(fragment,email,password,firstName,lastName)
    }

    override suspend fun getLogin(fragment: Fragment, email: EditText, password: EditText) {
        dataSource.getLogin(fragment,email,password)
    }

}