package com.enzoftware.androidgraphql.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.enzoftware.androidgraphql.R
import com.enzoftware.androidgraphql.databinding.ProductItemBinding
import com.enzoftware.androidgraphql.domain.model.Product


/**
 * Created by Enzo Lizama Paredes on 12/1/20.
 * Contact: lizama.enzo@gmail.com
 */
class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val itemList: MutableList<Product> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(
            ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = itemList[position]
        product.run { holder.bind(product) }
    }

    fun set(items: List<Product>) {
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    fun size() = itemList.size

    override fun getItemCount(): Int = size()

    inner class ProductViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            Glide
                .with(binding.root)
                .load(product.image)
                .centerCrop()
                .placeholder(R.drawable.justo)
                .into(binding.productImage)

            binding.productName.text = product.name
            binding.productDescription.text = product.description
        }
    }
}

