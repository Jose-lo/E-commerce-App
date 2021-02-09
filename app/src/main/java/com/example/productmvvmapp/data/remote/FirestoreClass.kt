package com.example.productmvvmapp.data.remote

import androidx.fragment.app.Fragment
import com.example.productmvvmapp.application.Constants
import com.example.productmvvmapp.data.model.User
import com.example.productmvvmapp.ui.fragments.LoginFragment
import com.google.firebase.auth.FirebaseAuth
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

    fun getUserDetails(fragment: Fragment) {
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->

                val user = document.toObject(User::class.java)!!
                when (fragment) {
                    is LoginFragment -> {
                        // Call a function of base activity for transferring the result to it.
                        fragment.userLoggedInSuccess(user)
                    }
                }

            }
            .addOnFailureListener { e ->

            }
    }
}