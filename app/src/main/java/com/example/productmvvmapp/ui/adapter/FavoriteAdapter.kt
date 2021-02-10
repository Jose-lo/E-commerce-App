package com.example.productmvvmapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.DiffResult.NO_POSITION
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productmvvmapp.core.BaseViewHolder
import com.example.productmvvmapp.data.model.Product
import com.example.productmvvmapp.databinding.ItemFavoriteRowBinding

class FavoriteAdapter (
        private val context: Context,
        private val itemClickListener: OnFavoriteClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var prodctList =  listOf<Product>()

    interface OnFavoriteClickListener{
        fun onFavoriteClick(product: Product,position: Int)

    }

    fun setProductList(prodctList : List<Product>){
        this.prodctList = prodctList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            ItemFavoriteRowBinding.inflate(LayoutInflater.from(context), parent, false)
        val holder = ProductViewHolder(itemBinding)
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is ProductViewHolder -> holder.bind(prodctList[position],position)

        }
    }

    override fun getItemCount(): Int = prodctList.size

    private inner class ProductViewHolder(
        private val binding: ItemFavoriteRowBinding
    ) : BaseViewHolder<Product>(binding.root) {
        override fun bind(item: Product,position: Int) {
            Glide.with(context).load(item.image)
                .centerCrop().into(binding.imgFavorite)
            binding.txtTitleFavorite.text = item.name
            binding.txtDeriptionFavorite.text = item.description

            binding.btnEraseFavorite.setOnClickListener {
                //itemClickListener.onFavoriteClick(item,position)

                 adapterPosition.takeIf { it != NO_POSITION }
                        ?: return@setOnClickListener

                itemClickListener.onFavoriteClick(prodctList[position],position)

            }
        }
    }
}