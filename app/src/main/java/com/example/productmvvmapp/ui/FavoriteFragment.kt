package com.example.productmvvmapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.map
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.productmvvmapp.R
import com.example.productmvvmapp.core.Resource
import com.example.productmvvmapp.data.local.AppDatabase
import com.example.productmvvmapp.data.local.LocalDataSource
import com.example.productmvvmapp.data.model.Product
import com.example.productmvvmapp.data.model.ProductEntity
import com.example.productmvvmapp.data.model.toProductEntity
import com.example.productmvvmapp.data.remote.RemoteDataSource
import com.example.productmvvmapp.databinding.FragmentFavoriteBinding
import com.example.productmvvmapp.presentation.MainViewModel
import com.example.productmvvmapp.presentation.MainViewModelProviders
import com.example.productmvvmapp.repository.ProductRepositoryImpl
import com.example.productmvvmapp.repository.RetrofitClient
import com.example.productmvvmapp.ui.adapter.FavoriteAdapter

class FavoriteFragment : Fragment(R.layout.fragment_favorite), FavoriteAdapter.OnFavoriteClickListener {

    private val viewmodel by viewModels<MainViewModel> { MainViewModelProviders(ProductRepositoryImpl(
        RemoteDataSource(RetrofitClient.webservice),
        LocalDataSource(AppDatabase.getDatabase(requireContext()).productDao())
    )) }

    private lateinit var favoritesAdapter: FavoriteAdapter
    private lateinit var binding : FragmentFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoritesAdapter = FavoriteAdapter(requireContext(),this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)
        setUpRecyclerview()
        binding.rvFavorites.adapter = favoritesAdapter

        viewmodel.getProductFavorites().observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading->{

                }
                is Resource.Success->{
                    val list = it.data.map {
                        Product(it.id,it.name,it.description,it.miniRating,it.totalRating,it.price,
                        it.cuttedPrec,it.descriptionLarge,it.image)
                    }
                    favoritesAdapter.setProductList(list)

                }
                is Resource.Failure->{

                }
            }
        })
    }

    private fun setUpRecyclerview(){
        binding.rvFavorites.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
    }

    override fun onFavoriteClick(product: Product, position: Int) {
        viewmodel.deleteProductFavorite(product)

    }
}