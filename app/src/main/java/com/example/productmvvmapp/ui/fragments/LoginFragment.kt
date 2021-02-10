package com.example.productmvvmapp.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.productmvvmapp.R
import com.example.productmvvmapp.application.toast
import com.example.productmvvmapp.data.local.AppDatabase
import com.example.productmvvmapp.data.local.LocalDataSource
import com.example.productmvvmapp.data.model.User
import com.example.productmvvmapp.data.remote.FirestoreClass
import com.example.productmvvmapp.data.remote.RemoteDataSource
import com.example.productmvvmapp.databinding.FragmentLoginBinding
import com.example.productmvvmapp.presentation.LoginViewModel
import com.example.productmvvmapp.presentation.MainViewModelProviders
import com.example.productmvvmapp.presentation.RegisterViewModel
import com.example.productmvvmapp.repository.ProductRepositoryImpl
import com.example.productmvvmapp.repository.RetrofitClient
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val mAuth = FirebaseAuth.getInstance()
    private val viewmodel by viewModels<LoginViewModel> { MainViewModelProviders(
        ProductRepositoryImpl(
            RemoteDataSource(RetrofitClient.webservice, FirestoreClass()),
            LocalDataSource(AppDatabase.getDatabase(requireContext()).productDao())
        )
    ) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        goToRegisterAndLogin()
        btnLogOut()
    }

    private fun goToRegisterAndLogin(){
        binding.tvRegister.setOnClickListener {findNavController().navigate(R.id.action_loginFragment_to_registerFragment)}
        binding.btnLogin.setOnClickListener { logInRegisteredUser() }

    }

    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etEmail.text.toString().trim { it <= ' ' }) -> {
                toast("Ingrese su correo")
                false
            }
            TextUtils.isEmpty(binding.etPassword.text.toString().trim { it <= ' ' }) -> {
                toast("Ingrese su contraseña")
                false
            }
            else -> {
                //showErrorSnackBar("Your details are valid.", false)
                true
            }
        }
    }

    private fun logInRegisteredUser(){
        if (validateLoginDetails()) {
            viewmodel.getLogin(this,binding.etEmail,binding.etPassword)
        }
    }

    override fun onStart() {
        super.onStart()
        if(mAuth.currentUser != null){
            binding.loginAuthContainer.visibility = View.VISIBLE
            binding.loginContainer.visibility = View.GONE
        }else{
            binding.loginAuthContainer.visibility = View.GONE
            binding.loginContainer.visibility = View.VISIBLE
        }
    }


    private fun btnLogOut(){
        binding.btnLogout.setOnClickListener {
            mAuth.signOut()
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }
    }
}