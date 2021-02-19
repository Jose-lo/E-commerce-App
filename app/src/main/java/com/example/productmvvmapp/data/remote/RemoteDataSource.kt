package com.example.productmvvmapp.data.remote

import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.productmvvmapp.core.Resource
import com.example.productmvvmapp.data.model.CartItem
import com.example.productmvvmapp.data.model.Product
import com.example.productmvvmapp.data.model.ProductList
import com.example.productmvvmapp.data.model.User
import com.example.productmvvmapp.repository.WebService

class RemoteDataSource(private val webService: WebService, private val firestoreClass: FirestoreClass) {

   suspend fun getAllProducts(): ProductList {
        return webService.getAllProducts()
    }

    suspend fun getMakeupProducts(): ProductList {
        return webService.getMakeupProducts()
    }

    fun setRegister(fragment: Fragment,email:EditText,password: EditText,firstName: EditText, lastName:EditText){
        firestoreClass.setRegister(fragment,email,password,firstName,lastName)
    }

    fun getLogin(fragment: Fragment, email: EditText, password: EditText){
        firestoreClass.getLogin(fragment,email,password)
    }

    fun addCartItems(addToCart: CartItem){
        firestoreClass.addCartItems(addToCart)
    }

    suspend fun fetchCartItems(): Resource<List<CartItem>>{
        return firestoreClass.fetchCartItems()
    }

}