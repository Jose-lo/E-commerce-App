package com.example.productmvvmapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class User(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val image: String = "",
    val mobile: Long = 0,
    val gender: String = "",
    val profileCompleted: Int = 0
)
