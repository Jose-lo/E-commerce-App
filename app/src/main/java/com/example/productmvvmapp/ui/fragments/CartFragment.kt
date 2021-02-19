package com.example.productmvvmapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.productmvvmapp.R
import com.example.productmvvmapp.application.hide
import com.example.productmvvmapp.application.show
import com.example.productmvvmapp.core.Resource
import com.example.productmvvmapp.data.local.AppDatabase
import com.example.productmvvmapp.data.local.LocalDataSource
import com.example.productmvvmapp.data.model.CartItem
import com.example.productmvvmapp.data.model.Product
import com.example.productmvvmapp.data.remote.FirestoreClass
import com.example.productmvvmapp.data.remote.RemoteDataSource
import com.example.productmvvmapp.databinding.FragmentCartBinding
import com.example.productmvvmapp.presentation.CartViewModel
import com.example.productmvvmapp.presentation.MainViewModelProviders
import com.example.productmvvmapp.repository.ProductRepositoryImpl
import com.example.productmvvmapp.repository.RetrofitClient
import com.example.productmvvmapp.ui.adapter.CartAdapter
import com.example.productmvvmapp.ui.adapter.CartItemAdapter
import com.google.firebase.auth.FirebaseAuth

class CartFragment : Fragment(R.layout.fragment_cart), CartItemAdapter.OnCartItemClickListener {
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartAdapter: CartItemAdapter
    private lateinit var mCartListItems: List<CartItem>
    private val mAuth = FirebaseAuth.getInstance()


    private val viewModel by viewModels<CartViewModel> {
        MainViewModelProviders(
            ProductRepositoryImpl
                (
                RemoteDataSource(RetrofitClient.webservice, FirestoreClass()),
                LocalDataSource(AppDatabase.getDatabase(requireContext()).productDao())
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartAdapter = CartItemAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCartBinding.bind(view)
        setUpRecyclerview()
        //getCarProducts()
        fetchCartProducts()

    }

    private fun setUpRecyclerview() {
        binding.rvCart.adapter = cartAdapter
    }

    private fun getCarProducts(){
        viewModel.getCartProducts().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {

                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Error: ${it.exception}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

    }

    private fun fetchCartProducts(){
        viewModel.fetchCartItems().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    initView(result.data)
                }

                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Error: ${result.exception}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

    }


    private fun initView(products: List<CartItem>){
        viewModel.resetAmount()
        cartAdapter.setCartList(products)
        viewModel.calculateTotalAmount(products)
        binding.textViewAmount.text = (" Total: $" + viewModel.getAmount())
    }


    override fun onStart() {
        super.onStart()
        if(mAuth.currentUser != null){
            binding.cartContainer.show()
            binding.emptyContainer.hide()
        }else{
            binding.cartContainer.hide()
            binding.emptyContainer.show()

        }
    }

    override fun onCartListener(product: CartItem, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCartQuantityListener(
        products: List<CartItem>,
        product: CartItem,
        position: Int
    ) {
        mCartListItems = products
        var subTotal: Double = 0.0
        for (product in mCartListItems) {
            val price = product.price.toInt()
            val quantity = product.stock_quantity.toInt()

            subTotal += (price * quantity)
        }
        binding.textViewAmount.setText("${subTotal}")
    }
}