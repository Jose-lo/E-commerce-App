package com.example.productmvvmapp.application

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(message: CharSequence,duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(requireContext(),message,duration).show()
fun Context.toast(message: CharSequence,duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this,message,duration).show()

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}