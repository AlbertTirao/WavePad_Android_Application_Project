package com.example.wavepad

import android.util.Log
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

    // Initialize filteredList with a copy of productList in the constructor
    private var filteredList: List<ProductDataClass> = productList.toList()

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.text_product_name)
        val priceTextView: TextView = itemView.findViewById(R.id.text_product_price)
        val imageProduct: ImageView = itemView.findViewById(R.id.image_product)
        val buyButton: Button = itemView.findViewById(R.id.button_buy_now)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(filteredList[position])
                    Log.d("ProductAdapter", "Clicked on item at position: $position")
                }
            }
            buyButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onBuyButtonClick(filteredList[position])
                    Log.d("ProductAdapter", "Clicked on buy button at position: $position")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = filteredList[position]
        holder.titleTextView.text = currentItem.title
        holder.priceTextView.text = currentItem.price
        holder.imageProduct.setImageResource(currentItem.imageResource)
    }

    override fun getItemCount() = filteredList.size

    fun filter(text: String?) {
        if (text.isNullOrEmpty()) {
            filteredList = productList
        } else {
            val searchText = text.toLowerCase().trim()
            filteredList = productList.filter { product ->
                product.title.toLowerCase().contains(searchText)
            }
        }
        notifyDataSetChanged()
    }
}
