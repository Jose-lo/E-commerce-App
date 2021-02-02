package com.example.productmvvmapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.productmvvmapp.R
import com.example.productmvvmapp.core.Resource
import com.example.productmvvmapp.data.local.AppDatabase
import com.example.productmvvmapp.data.local.LocalDataSource
import com.example.productmvvmapp.data.model.Product
import com.example.productmvvmapp.data.remote.RemoteDataSource
import com.example.productmvvmapp.databinding.FragmentMainBinding
import com.example.productmvvmapp.presentation.MainViewModel
import com.example.productmvvmapp.presentation.MainViewModelProviders
import com.example.productmvvmapp.repository.ProductRepositoryImpl
import com.example.productmvvmapp.repository.RetrofitClient
import com.example.productmvvmapp.ui.fragments.MainFragmentDirections
import com.example.productmvvmapp.ui.adapter.ProductAdapter
import com.example.productmvvmapp.ui.concat.AllProductsConcatAdapter
import com.example.productmvvmapp.ui.concat.MakeupProductsConcatAdapter

class MainFragment : Fragment(R.layout.fragment_main),ProductAdapter.OnProductClickListener {

    private lateinit var binding : FragmentMainBinding
    private val viewmodel by viewModels<MainViewModel> { MainViewModelProviders(ProductRepositoryImpl(
        RemoteDataSource(RetrofitClient.webservice),
        LocalDataSource(AppDatabase.getDatabase(requireContext()).productDao())
    )) }

    private lateinit var concatAdapter: ConcatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding = FragmentMainBinding.bind(view)

        concatAdapter = ConcatAdapter()

        viewmodel.getAllProductList().observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading->{
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success->{
                    binding.progressBar.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(0,AllProductsConcatAdapter(ProductAdapter(it.data.first.products,this@MainFragment)))
                        addAdapter(1,MakeupProductsConcatAdapter(ProductAdapter(it.data.second.products,this@MainFragment)))
                    }
                    binding.rvProducts.adapter = concatAdapter
                }
                is Resource.Failure->{
                    binding.progressBar.visibility = View.GONE
                    Log.d("error","${it.exception}")
                }
            }
        })

    }

    override fun onProductClick(product: Product) {
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(
                product.name,
                product.description,
                product.miniRating.toFloat(),
                product.totalRating.toFloat(),
                product.price.toFloat(),
                product.cuttedPrec.toFloat(),
                product.descriptionLarge,
                product.image, product.id,
                product.quantity
        )
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favoritos -> {
                findNavController().navigate(R.id.action_mainFragment_to_favoriteFragment)
                false
            }
            R.id.cart ->{
                findNavController().navigate(R.id.action_mainFragment_to_cartFragment)
                false
            }
            else -> false
        }
    }
}