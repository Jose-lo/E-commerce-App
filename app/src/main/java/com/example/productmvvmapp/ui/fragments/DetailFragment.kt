package com.example.productmvvmapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.productmvvmapp.R
import com.example.productmvvmapp.application.Constants
import com.example.productmvvmapp.data.local.AppDatabase
import com.example.productmvvmapp.data.local.LocalDataSource
import com.example.productmvvmapp.data.model.CarEntity
import com.example.productmvvmapp.data.model.CartItem
import com.example.productmvvmapp.data.model.Product
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
import kotlinx.coroutines.launch

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()
    private val mAuth = FirebaseAuth.getInstance()
    private var isProductFavorite: Boolean? = null
    private lateinit var product: Product
    var mProductID : String = ""
    private val viewmodel by viewModels<DetailViewModel> {
        MainViewModelProviders(
            ProductRepositoryImpl(
                RemoteDataSource(RetrofitClient.webservice, FirestoreClass()),
                LocalDataSource(AppDatabase.getDatabase(requireContext()).productDao())
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireArguments().let {
            DetailFragmentArgs.fromBundle(it).also { args ->
                product = Product(
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
            }
        }
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
        sendFavorites()
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
                val isCocktailFavorited = isProductFavorite ?: return@setOnClickListener
                val addCartItem = CartItem(
                    FirestoreClass().getCurrentUserID(),
                    mProductID,
                    product.name,
                    product.price.toString(),
                    product.image,
                    Constants.DEFAULT_CART_QUANTITY
                )
                viewmodel.saveOrDeleteCartItem(product,addCartItem)
                this.isProductFavorite = !isCocktailFavorited
                updateButtonIcon()

            } else {
                findNavController().navigate(R.id.action_detailFragment_to_loginFragment)
            }
        }
    }

    private  fun updateButtonIcon() {
        val isCocktailFavorited = isProductFavorite ?: return

        binding.btnSaveCart.setImageResource(
            when {
                isCocktailFavorited -> R.drawable.ic_baseline_delete_24
                else -> R.drawable.ic_baseline_shopping_cart_24
            }
        )
    }

    private fun sendFavorites(){
        viewLifecycleOwner.lifecycleScope.launch {
            isProductFavorite = viewmodel.isInsertCart(product)
            updateButtonIcon()
        }
    }
}