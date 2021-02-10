package com.example.productmvvmapp.presentation

import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.productmvvmapp.core.Resource
import com.example.productmvvmapp.data.model.User
import com.example.productmvvmapp.data.remote.FirestoreClass
import com.example.productmvvmapp.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val repo: ProductRepository) : ViewModel() {

    fun setRegister(
        fragment: Fragment,
        email: EditText,
        password: EditText,
        firstName: EditText,
        lastName: EditText
    ) {
      viewModelScope.launch {
          repo.setRegister(fragment,email,password,firstName,lastName)
      }
    }
}