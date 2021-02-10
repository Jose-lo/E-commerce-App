package com.example.productmvvmapp.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.productmvvmapp.R
import com.example.productmvvmapp.application.toast
import com.example.productmvvmapp.data.local.AppDatabase
import com.example.productmvvmapp.data.local.LocalDataSource
import com.example.productmvvmapp.data.model.User
import com.example.productmvvmapp.data.remote.FirestoreClass
import com.example.productmvvmapp.data.remote.RemoteDataSource
import com.example.productmvvmapp.databinding.FragmentRegisterBinding
import com.example.productmvvmapp.presentation.MainViewModel
import com.example.productmvvmapp.presentation.MainViewModelProviders
import com.example.productmvvmapp.presentation.RegisterViewModel
import com.example.productmvvmapp.repository.ProductRepositoryImpl
import com.example.productmvvmapp.repository.RetrofitClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private val viewmodel by viewModels<RegisterViewModel> { MainViewModelProviders(
        ProductRepositoryImpl(
        RemoteDataSource(RetrofitClient.webservice, FirestoreClass()),
        LocalDataSource(AppDatabase.getDatabase(requireContext()).productDao())
    )
    ) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        binding.btnRegister.setOnClickListener {
            registerUser()
        }
        btnGoLogin()
    }

    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etFirstName.text.toString().trim { it <= ' ' }) -> {
                toast("Escriba su nombre")
                false
            }

            TextUtils.isEmpty(binding.etLastName.text.toString().trim { it <= ' ' }) -> {
                toast("Escriba su apellido")
                false
            }

            TextUtils.isEmpty(binding.etEmail.text.toString().trim { it <= ' ' }) -> {
                toast("Escriba su correo")
                false
            }

            TextUtils.isEmpty(binding.etPassword.text.toString().trim { it <= ' ' }) -> {
                toast("Escriba su contraseña")
                false
            }

            TextUtils.isEmpty(binding.etConfirmPassword.text.toString().trim { it <= ' ' }) -> {
                toast("Confirme su contraseña")
                false
            }

            binding.etPassword.text.toString().trim { it <= ' ' } != binding.etConfirmPassword.text.toString()
                .trim { it <= ' ' } -> {
                toast("Sus contraseñas no coinciden")
                false
            }
            !binding.cbTermsAndCondition.isChecked -> {
                toast("Acepte terminos y condiciones")
                false
            }
            else -> {
                toast("Registro exitoso")
                true
            }
        }
    }

    private fun registerUser() {
        if (validateRegisterDetails()) {
            viewmodel.setRegister(
                this,
                binding.etEmail,
                binding.etPassword,
                binding.etFirstName,
                binding.etLastName
            )
        }
    }


    private fun btnGoLogin(){
        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}