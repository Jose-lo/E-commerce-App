package com.example.productmvvmapp.ui.concat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.productmvvmapp.core.BaseConcatHolder
import com.example.productmvvmapp.databinding.AllProductsRowBinding
import com.example.productmvvmapp.databinding.MakeupProductsRowBinding
import com.example.productmvvmapp.ui.adapter.ProductAdapter

class MakeupProductsConcatAdapter (private val productsAdapter: ProductAdapter): RecyclerView.Adapter<BaseConcatHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {
        val itemBinding = MakeupProductsRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ConcatViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        when(holder){
            is ConcatViewHolder-> holder.bind(productsAdapter)
        }
    }

    override fun getItemCount(): Int = 1

    private inner class ConcatViewHolder(private val binding: MakeupProductsRowBinding): BaseConcatHolder<ProductAdapter>(binding.root){
        override fun bind(adapter: ProductAdapter) {
            binding.rvMakeupProducts.adapter = adapter
        }
    }
}