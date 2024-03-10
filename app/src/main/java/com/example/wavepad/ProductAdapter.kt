package com.example.wavepad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class ProductAdapter(
    private var productList: List<ProductDataClass>,
    private val onItemClick: (ProductDataClass) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private var filteredList: List<ProductDataClass> = productList.toList()

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.text_product_name)
        private val authorTextView: TextView = itemView.findViewById(R.id.text_author)
        private val priceTextView: TextView = itemView.findViewById(R.id.text_product_price)
        private val imageProduct: ImageView = itemView.findViewById(R.id.image_product)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(filteredList[position])
                }
            }
        }

        private fun loadAuthorName(author: AuthorDataClass?, callback: (String) -> Unit) {
            if (author != null) {
                callback("Author: ${author.name}")
            } else {
                callback("Author: Unknown Author")
            }
        }

        fun bind(product: ProductDataClass) {
            titleTextView.text = product.product_name
            priceTextView.text = "₱${product.product_price}"
            loadAuthorName(product.author) { authorName ->
                authorTextView.text = authorName
            }

            val imageUrl = "https://wavepad-ecom-529a3cf49f8f.herokuapp.com/front/images/product_images/large/${product.product_image}"
            Glide.with(itemView.context)
                .load(imageUrl)
                .error(R.drawable.baseline_error_24)
                .into(imageProduct)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(filteredList[position])
    }

    override fun getItemCount() = filteredList.size

    fun filter(query: String?) {
        if (query.isNullOrBlank()) {
            filteredList = productList.toList()
        } else {
            filteredList = productList.filter { product ->
                product.product_name.contains(query, ignoreCase = true) ||
                        product.author_id.contains(query, ignoreCase = true) ||
                        product.category_id.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

    fun updateData(newList: List<ProductDataClass>) {
        productList = newList
        filteredList = newList
        notifyDataSetChanged()
    }
}
