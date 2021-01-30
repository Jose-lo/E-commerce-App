package com.example.productmvvmapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.productmvvmapp.R
import com.example.productmvvmapp.core.Resource
import com.example.productmvvmapp.data.local.AppDatabase
import com.example.productmvvmapp.data.local.LocalDataSource
import com.example.productmvvmapp.data.model.Product
import com.example.productmvvmapp.data.remote.RemoteDataSource
import com.example.productmvvmapp.databinding.FragmentCartBinding
import com.example.productmvvmapp.presentation.CartViewModel
import com.example.productmvvmapp.presentation.MainViewModelProviders
import com.example.productmvvmapp.repository.ProductRepositoryImpl
import com.example.productmvvmapp.repository.RetrofitClient
import com.example.productmvvmapp.ui.adapter.CartAdapter

class CartFragment : Fragment(R.layout.fragment_cart), CartAdapter.OnCartClickListener {

    private lateinit var binding: FragmentCartBinding
    private lateinit var cartAdapter:CartAdapter
    private val viewmodel by viewModels<CartViewModel> {
        MainViewModelProviders(ProductRepositoryImpl
        (RemoteDataSource(RetrofitClient.webservice),
                LocalDataSource(AppDatabase.getDatabase(requireContext()).productDao())))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartAdapter = CartAdapter(requireContext(),this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCartBinding.bind(view)
        setUpRecyclerview()

        viewmodel.getCartProducts().observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading->{
                }
                is Resource.Success->{
                    cartAdapter.setCartList(it.data)
                }
                is Resource.Failure->{
                    Toast.makeText(requireContext(),"Error: ${it.exception}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setUpRecyclerview(){
        binding.rvCart.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
        binding.rvCart.adapter = cartAdapter
    }

    override fun onCartListener(product: Product, position: Int) {
        viewmodel.deleteCartFavorite(product)
    }
}