package com.example.productmvvmapp.presentation

import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmvvmapp.data.model.User
import com.example.productmvvmapp.repository.ProductRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repo: ProductRepository): ViewModel() {

    fun getLogin(fragment: Fragment, email: EditText, password: EditText){
        viewModelScope.launch {
            repo.getLogin(fragment,email,password)
        }
    }
}