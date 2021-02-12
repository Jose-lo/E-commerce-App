package com.example.productmvvmapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmvvmapp.data.model.*
import com.example.productmvvmapp.repository.ProductRepository
import kotlinx.coroutines.launch

class DetailViewModel(private  val repo: ProductRepository): ViewModel() {

    fun saveProduct(product : ProductEntity){
        viewModelScope.launch {
            repo.insertProduct(product)
        }
    }

    suspend fun isInsertCart(product: Product): Boolean =
        repo.isCartInsert(product)

    fun saveOrDeleteCartItem(product: Product,addToCart: CartItem){
        viewModelScope.launch {
            if(repo.isCartInsert(product)){
                repo.deleteCartFavorite(product)
            } else{
                repo.insertCartFavorite(product)
                repo.addCartItems(addToCart)
            }
        }
    }
}