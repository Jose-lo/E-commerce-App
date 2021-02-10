package com.example.productmvvmapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.productmvvmapp.R
import com.example.productmvvmapp.data.local.AppDatabase
import com.example.productmvvmapp.data.local.LocalDataSource
import com.example.productmvvmapp.data.model.CarEntity
import com.example.productmvvmapp.data.model.ProductEntity
import com.example.productmvvmapp.data.remote.FirestoreClass
import com.example.productmvvmapp.data.remote.RemoteDataSource
import com.example.productmvvmapp.databinding.FragmentDetailBinding
import com.example.productmvvmapp.presentation.DetailViewModel
import com.example.productmvvmapp.presentation.MainViewModel
import com.example.productmvvmapp.presentation.MainViewModelProviders
import com.example.productmvvmapp.repository.ProductRepositoryImpl
import com.example.productmvvmapp.repository.RetrofitClient
import com.example.productmvvmapp.ui.fragments.DetailFragment
import com.google.firebase.auth.FirebaseAuth

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()
    private val mAuth = FirebaseAuth.getInstance()
    private val viewmodel by viewModels<DetailViewModel> {
        MainViewModelProviders(
            ProductRepositoryImpl(
                RemoteDataSource(RetrofitClient.webservice, FirestoreClass()),
                LocalDataSource(AppDatabase.getDatabase(requireContext()).productDao())
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        Glide.with(requireContext()).load(args.image).centerCrop().into(binding.imgBackground)
        Glide.with(requireContext()).load(args.image).centerCrop().into(binding.imgProduct)
        binding.txtDescription.text = args.descriptionLarge
        binding.txtProductTitle.text = args.name
        binding.txtRating.text = " ${args.totalRating} Ratings"
        binding.txtPrice.text = "$ ${args.price} MXN"
        saveFavorites()
        saveCartItems()
    }

    private fun saveFavorites() {
        binding.btnSaveFavorites.setOnClickListener {
            viewmodel.saveProduct(
                ProductEntity(
                    args.id,
                    args.name,
                    args.description,
                    args.miniRating.toDouble(),
                    args.totalRating.toInt(),
                    args.price.toDouble(),
                    args.quantity,
                    args.cuttedPrec.toDouble(),
                    args.descriptionLarge,
                    args.image
                )
            )
            Toast.makeText(requireContext(), "Se guardo en favoritos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveCartItems() {

        binding.btnSaveCart.setOnClickListener {
            if (mAuth.currentUser != null) {
                viewmodel.insertCartFavorite(
                    CarEntity(
                        args.id,
                        args.name,
                        args.description,
                        args.miniRating.toDouble(),
                        args.totalRating.toInt(),
                        args.quantity,
                        args.price.toDouble(),
                        args.cuttedPrec.toDouble(),
                        args.descriptionLarge,
                        args.image
                    )
                )
                Toast.makeText(requireContext(), "Se guardo la cesta", Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(R.id.action_detailFragment_to_loginFragment)
            }
        }
    }
}