package com.example.productmvvmapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.productmvvmapp.R
import com.example.productmvvmapp.core.Resource
import com.example.productmvvmapp.data.local.AppDatabase
import com.example.productmvvmapp.data.local.LocalDataSource
import com.example.productmvvmapp.data.remote.RemoteDataSource
import com.example.productmvvmapp.databinding.FragmentCartBinding
import com.example.productmvvmapp.presentation.CartViewModel
import com.example.productmvvmapp.presentation.MainViewModelProviders
import com.example.productmvvmapp.repository.ProductRepositoryImpl
import com.example.productmvvmapp.repository.RetrofitClient

class CartFragment : Fragment(R.layout.fragment_cart) {

    private lateinit var binding: FragmentCartBinding
    private val viewmodel by viewModels<CartViewModel> {
        MainViewModelProviders(ProductRepositoryImpl
        (RemoteDataSource(RetrofitClient.webservice),
                LocalDataSource(AppDatabase.getDatabase(requireContext()).productDao())))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCartBinding.bind(view)

        viewmodel.getCartProducts().observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading->{
                    Log.d("LIVEDATA","loading...")
                }
                is Resource.Success->{
                    Log.d("LIVEDATA","${it.data}")
                }
                is Resource.Failure->{
                    Log.d("LIVEDATA","${it.exception}")
                }
            }
        })
    }
}