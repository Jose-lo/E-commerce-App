package com.example.productmvvmapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmvvmapp.data.model.CarEntity
import com.example.productmvvmapp.data.model.ProductEntity
import com.example.productmvvmapp.repository.ProductRepository
import kotlinx.coroutines.launch

class DetailViewModel(private  val repo: ProductRepository): ViewModel() {

    fun saveProduct(product : ProductEntity){
        viewModelScope.launch {
            repo.insertProduct(product)
        }
    }

    fun insertCartFavorite(product: CarEntity){
        viewModelScope.launch {
            repo.insertCartFavorite(product)
        }
    }
}