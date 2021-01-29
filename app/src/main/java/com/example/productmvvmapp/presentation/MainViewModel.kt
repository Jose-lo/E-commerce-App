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

    fun saveProduct(product : ProductEntity){
        viewModelScope.launch {
            repo.insertProduct(product)
        }
    }

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

class MainViewModelProviders(private val repo: ProductRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ProductRepository::class.java).newInstance(repo)
    }
}