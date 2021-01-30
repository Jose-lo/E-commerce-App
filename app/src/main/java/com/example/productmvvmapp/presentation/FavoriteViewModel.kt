package com.example.productmvvmapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.productmvvmapp.core.Resource
import com.example.productmvvmapp.data.model.Product
import com.example.productmvvmapp.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repo: ProductRepository):ViewModel() {

    fun getProductFavorites() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emitSource(repo.getProductFavorite().map { Resource.Success(it) })
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun deleteProductFavorite(product: Product){
        viewModelScope.launch {
            repo.deleteProductFavorite(product)
        }
    }
}