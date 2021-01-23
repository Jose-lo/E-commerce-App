package com.example.productmvvmapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.productmvvmapp.R
import com.example.productmvvmapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        Glide.with(requireContext()).load(args.image).centerCrop().into(binding.imgBackground)
        Glide.with(requireContext()).load(args.image).centerCrop().into(binding.imgProduct)
        binding.txtDescription.text = args.descriptionLarge
        binding.txtProductTitle.text = args.name
        binding.txtRating.text = " ${args.totalRating} Ratings"
        binding.txtPrice.text = "$ ${args.price} MXN"

    }
}