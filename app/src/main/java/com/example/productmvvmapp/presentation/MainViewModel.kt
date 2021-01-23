package com.example.productmvvmapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.productmvvmapp.core.Resource
import com.example.productmvvmapp.repository.ProductRepository
import kotlinx.coroutines.Dispatchers

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

class MainViewModelProviders(private val repo: ProductRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ProductRepository::class.java).newInstance(repo)
    }
}