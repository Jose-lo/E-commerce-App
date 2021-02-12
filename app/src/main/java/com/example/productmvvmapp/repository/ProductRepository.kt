package com.example.productmvvmapp.repository

import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.productmvvmapp.core.Resource
import com.example.productmvvmapp.data.model.*

interface ProductRepository {

    suspend fun getAllProductList(): ProductList
    suspend fun getMakeupProducts(): ProductList

    suspend fun getProductFavorite(): LiveData<List<Product>>
    suspend fun insertProduct(product: ProductEntity)
    suspend fun deleteProductFavorite(product: Product)

    suspend fun getCartProducts(): LiveData<List<Product>>
    suspend fun insertCartFavorite(product: Product)
    suspend fun deleteCartFavorite(product: Product)

    suspend fun setRegister(fragment: Fragment,email:EditText,password: EditText,firstName: EditText, lastName:EditText)
    suspend fun getLogin(fragment: Fragment, email: EditText, password: EditText)
    suspend fun isCartInsert(product: Product): Boolean
    suspend fun addCartItems(addToCart: CartItem)
}