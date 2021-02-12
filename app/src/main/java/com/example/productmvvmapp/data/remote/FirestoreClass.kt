package com.example.productmvvmapp.data.remote

import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.productmvvmapp.R
import com.example.productmvvmapp.application.Constants
import com.example.productmvvmapp.application.toast
import com.example.productmvvmapp.data.model.CarEntity
import com.example.productmvvmapp.data.model.CartItem
import com.example.productmvvmapp.data.model.Product
import com.example.productmvvmapp.data.model.User
import com.example.productmvvmapp.ui.fragments.DetailFragment
import com.example.productmvvmapp.ui.fragments.LoginFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID
    }

    fun registerUser(userInfo: User) {

        mFireStore.collection(Constants.USERS)
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {

            }
            .addOnFailureListener { e ->

            }
    }

    fun getLogin(fragment: Fragment,email: EditText, password: EditText){

        val _email = email.text.toString().trim { it <= ' ' }
        val _password = password.text.toString().trim { it <= ' ' }

        // Log-In using FirebaseAuth
        FirebaseAuth.getInstance().signInWithEmailAndPassword(_email, _password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    findNavController(fragment).navigate(R.id.action_loginFragment_to_mainFragment)
                } else {
                    fragment.toast("Error: ${task.exception.toString()}")
                }
            }
    }

    fun setRegister(fragment: Fragment,email:EditText,password: EditText,firstName: EditText, lastName:EditText){
        val _email: String = email.text.toString().trim { it <= ' ' }
        val _password: String = password.text.toString().trim { it <= ' ' }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(_email, _password)
            .addOnCompleteListener(
                OnCompleteListener<AuthResult> { task ->

                    if (task.isSuccessful) {

                        val firebaseUser: FirebaseUser = task.result!!.user!!

                        val user = User(
                            firebaseUser.uid,
                            firstName.text.toString().trim { it <= ' ' },
                            lastName.text.toString().trim { it <= ' ' },
                            email.text.toString().trim { it <= ' ' }
                        )
                        registerUser(user)
                        findNavController(fragment).navigate(R.id.action_registerFragment_to_loginFragment)

                    } else {
                        fragment.toast("Error: ${task.exception.toString()}")
                    }
                })
    }

    fun addCartItems(product: Product) {

        val addToCart = CartItem()
        var mProductID : String = ""

        mFireStore.collection(Constants.CART_ITEMS)
            .document()

            .set(addToCart, SetOptions.merge())
            .addOnSuccessListener {

                 CartItem(
                    getCurrentUserID(),
                    mProductID,
                    product.name,
                    product.price.toString(),
                    product.image,
                    Constants.DEFAULT_CART_QUANTITY
                )

            }
            .addOnFailureListener { e ->

            }
    }
}