package com.example.wavepad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private var productList: List<ProductDataClass>,
    private val onItemClick: (ProductDataClass) -> Unit,
    private val onBuyButtonClick: (ProductDataClass) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var filteredList: List<ProductDataClass> = productList.toList()

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.text_product_name)
        private val authorTextView: TextView = itemView.findViewById(R.id.text_author)
        private val priceTextView: TextView = itemView.findViewById(R.id.text_product_price)
        private val imageProduct: ImageView = itemView.findViewById(R.id.image_product)
        private val buyButton: Button = itemView.findViewById(R.id.button_buy_now)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(filteredList[position])
                }
            }
            buyButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onBuyButtonClick(filteredList[position])
                }
            }
        }

        fun bind(product: ProductDataClass) {
            titleTextView.text = product.product_name
            authorTextView.text = product.author_id
            priceTextView.text = "$${product.product_price}"
            imageProduct.setImageResource(product.product_image)
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
