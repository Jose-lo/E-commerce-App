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
import com.example.productmvvmapp.databinding.FragmentRegisterBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        binding.btnRegister.setOnClickListener {
            registerUser()
            btnGoLogin()
        }
    }

    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etFirstName.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(requireContext(), "Escriba su nombre", Toast.LENGTH_SHORT).show()
                false
            }

            TextUtils.isEmpty(binding.etLastName.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(requireContext(), "Escriba su apellido", Toast.LENGTH_SHORT).show()
                false
            }

            TextUtils.isEmpty(binding.etEmail.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(requireContext(), "Escriba su correo", Toast.LENGTH_SHORT).show()
                false
            }

            TextUtils.isEmpty(binding.etPassword.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(requireContext(), "Escriba su contraseña", Toast.LENGTH_SHORT).show()
                false
            }

            TextUtils.isEmpty(binding.etConfirmPassword.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(requireContext(), "Confirme su contraseña", Toast.LENGTH_SHORT).show()
                false
            }

            binding.etPassword.text.toString().trim { it <= ' ' } != binding.etConfirmPassword.text.toString()
                .trim { it <= ' ' } -> {
                Toast.makeText(requireContext(), "Sus contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                false
            }
            !binding.cbTermsAndCondition.isChecked -> {
                Toast.makeText(requireContext(), "Acepte terminos y condiciones", Toast.LENGTH_SHORT).show()
                false
            }
            else -> {

                true
            }
        }
    }

    private fun registerUser() {


        if (validateRegisterDetails()) {


            val email: String = binding.etEmail.text.toString().trim { it <= ' ' }
            val password: String = binding.etPassword.text.toString().trim { it <= ' ' }


            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->

                        if (task.isSuccessful) {

                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            val user = User(
                                firebaseUser.uid,
                                binding.etFirstName.text.toString().trim { it <= ' ' },
                                binding.etLastName.text.toString().trim { it <= ' ' },
                                binding.etEmail.text.toString().trim { it <= ' ' }
                            )

                            FirestoreClass().registerUser(user)

                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

                        } else {
                            Toast.makeText(requireContext(), "Error ${task.exception.toString()}", Toast.LENGTH_SHORT).show()
                        }
                    })
        }
    }

    private fun btnGoLogin(){
        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}