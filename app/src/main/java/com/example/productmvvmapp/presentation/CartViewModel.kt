package com.example.productmvvmapp.presentation

import androidx.lifecycle.*
import com.example.productmvvmapp.core.Resource
import com.example.productmvvmapp.data.model.Product
import com.example.productmvvmapp.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(private val repo: ProductRepository): ViewModel() {

    val textValue : MutableLiveData<String> = MutableLiveData("")

    fun getCartProducts() = liveData(viewModelScope.coroutineContext + Dispatchers.IO){
        emit(Resource.Loading())

        try {
            emitSource(repo.getCartProducts().map { Resource.Success(it) })
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun deleteCartFavorite(product: Product){
        viewModelScope.launch {
            repo.deleteCartFavorite(product)
        }
    }
}