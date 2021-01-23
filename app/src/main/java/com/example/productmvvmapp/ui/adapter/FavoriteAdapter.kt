package com.example.productmvvmapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productmvvmapp.core.BaseViewHolder
import com.example.productmvvmapp.data.model.Product
import com.example.productmvvmapp.databinding.ItemFavoriteRowBinding
import com.example.productmvvmapp.databinding.ProductItemBinding

class FavoriteAdapter (
    private val prodctList: List<Product>,
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            ItemFavoriteRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ProductViewHolder(itemBinding, parent.context)
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is ProductViewHolder -> holder.bind(prodctList[position])
        }
    }

    override fun getItemCount(): Int = prodctList.size

    private inner class ProductViewHolder(
        private val binding: ItemFavoriteRowBinding,
        val context: Context
    ) : BaseViewHolder<Product>(binding.root) {
        override fun bind(item: Product) {
            Glide.with(context).load(item.image)
                .centerCrop().into(binding.imgFavorite)
            binding.txtTitleFavorite.text = item.name
            binding.txtDeriptionFavorite.text = item.description
        }
    }
}