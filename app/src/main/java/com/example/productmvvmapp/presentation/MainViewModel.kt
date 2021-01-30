package com.example.productmvvmapp.presentation

import androidx.lifecycle.*
import com.example.productmvvmapp.core.Resource
import com.example.productmvvmapp.data.model.Product
import com.example.productmvvmapp.data.model.ProductEntity
import com.example.productmvvmapp.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repo: ProductRepository):ViewModel() {

    fun getAllProductList() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(Pair(repo.getAllProductList(),repo.getMakeupProducts())))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}
