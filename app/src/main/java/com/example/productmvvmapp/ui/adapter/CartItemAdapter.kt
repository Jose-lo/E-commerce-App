package com.example.productmvvmapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productmvvmapp.application.Constants
import com.example.productmvvmapp.core.BaseViewHolder
import com.example.productmvvmapp.data.model.CartItem
import com.example.productmvvmapp.data.model.Product
import com.example.productmvvmapp.data.remote.FirestoreClass
import com.example.productmvvmapp.databinding.ItemCartRowBinding
import com.example.productmvvmapp.ui.fragments.DetailFragment

class CartItemAdapter(private val itemClickListener: OnCartItemClickListener ):
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var cartList = listOf<CartItem>()

    interface OnCartItemClickListener {
        fun onCartListener(product: CartItem, position: Int)
        fun onCartQuantityListener(products: List<CartItem>, product: CartItem, position: Int)
    }

    fun setCartList(cartList: List<CartItem>) {
        this.cartList = cartList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ItemCartRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = CartItemViewHolder(itemBinding,parent.context)


        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is CartItemAdapter.CartItemViewHolder -> holder.bind(cartList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    private inner class CartItemViewHolder(val binding: ItemCartRowBinding, val context: Context): BaseViewHolder<CartItem>(binding.root){
        override fun bind(item: CartItem, position: Int) {

            val model = cartList[position]

            Glide.with(context).load(item.image).centerCrop().into(binding.imgCart)
            binding.txtTitleCart.text = item.title
            binding.txtPriceCart.text = item.price
            binding.amountCart.text = item.stock_quantity


            binding.btnEraseCart.setOnClickListener {
                FirestoreClass().removeItemFromCart(context, model.id)
            }

            binding.plusCart.setOnClickListener {

                val cartQuantity: Int = model.cart_quantity.toInt()

                if (cartQuantity < model.stock_quantity.toInt()) {

                    val itemHashMap = HashMap<String, Any>()

                    itemHashMap[Constants.CART_QUANTITY] = (cartQuantity + 1).toString()

                    FirestoreClass().updateMyCart(context, model.id, itemHashMap)
                }
            }

            binding.miniumCart.setOnClickListener {
                if (model.cart_quantity == "1") {
                    FirestoreClass().removeItemFromCart(context, model.id)
                } else {

                    val cartQuantity: Int = model.cart_quantity.toInt()

                    val itemHashMap = HashMap<String, Any>()

                    itemHashMap[Constants.CART_QUANTITY] = (cartQuantity - 1).toString()

                    FirestoreClass().updateMyCart(context, model.id, itemHashMap)
                }
                // END
            }


        }
    }


}