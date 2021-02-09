package com.example.productmvvmapp.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.productmvvmapp.R
import com.example.productmvvmapp.data.model.User
import com.example.productmvvmapp.data.remote.FirestoreClass
import com.example.productmvvmapp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val mAuth = FirebaseAuth.getInstance()

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
                Toast.makeText(requireContext(), "Ingrese su correo", Toast.LENGTH_SHORT).show()
                false
            }
            TextUtils.isEmpty(binding.etPassword.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(requireContext(), "Ingrese su contraseña", Toast.LENGTH_SHORT).show()
                false
            }
            else -> {
                //showErrorSnackBar("Your details are valid.", false)
                true
            }
        }
    }

    private fun logInRegisteredUser() {

        if (validateLoginDetails()) {


            val email = binding.etEmail.text.toString().trim { it <= ' ' }
            val password = binding.etPassword.text.toString().trim { it <= ' ' }

            // Log-In using FirebaseAuth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        FirestoreClass().getUserDetails(this)

                    } else {
                        Toast.makeText(requireContext(), "Error ${task.exception.toString()}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    fun userLoggedInSuccess(user: User) {
        if (user.profileCompleted == 0) {
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
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