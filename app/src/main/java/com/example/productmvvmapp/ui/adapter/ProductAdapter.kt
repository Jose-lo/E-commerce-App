package com.example.productmvvmapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productmvvmapp.core.BaseViewHolder
import com.example.productmvvmapp.data.model.Product
import com.example.productmvvmapp.databinding.ProductItemBinding

class ProductAdapter (
    private val prodctList: List<Product>,
    private val itemClickListener: OnProductClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnProductClickListener {
        fun onProductClick(product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ProductViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position =
                holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            itemClickListener.onProductClick(prodctList[position])
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is ProductViewHolder -> holder.bind(prodctList[position])
        }
    }

    override fun getItemCount(): Int = prodctList.size

    private inner class ProductViewHolder(
        private val binding: ProductItemBinding,
        val context: Context
    ) : BaseViewHolder<Product>(binding.root) {
        override fun bind(item: Product) {
            Glide.with(context).load(item.image)
                .centerCrop().into(binding.imgProduct)
            binding.txtProduct.text = item.name
        }
    }
}