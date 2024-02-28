package com.example.wavepad

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomePage : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var searchView: SearchView

    private lateinit var recyclerView: RecyclerView
    private lateinit var productList: MutableList<ProductDataClass>
    private lateinit var productService: ProductService
    private lateinit var productAdapter: ProductAdapter
    private lateinit var filteredList: List<ProductDataClass>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        productService = ProductService()

        productList = mutableListOf()
        filteredList = emptyList()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        productAdapter = ProductAdapter(productList,
            onItemClick = { product ->
                val intent = Intent(this, ProductFullDetail::class.java)
                intent.putExtra("PRODUCT", product)
                startActivity(intent)
            },
            onBuyButtonClick = { product ->
                val intent = Intent(this, ProductFullDetail::class.java)
                intent.putExtra("PRODUCT", product)
                startActivity(intent)
                Log.d("HomePage", "Clicked Buy Now for product: ${product.title}")
            }
        )

        recyclerView.adapter = productAdapter

        addCategories()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)

        drawerLayout = findViewById(R.id.drawer_layout)
        bottomNav = findViewById(R.id.bottomNavigationView)

        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> true
                R.id.chat -> {
                    startNewActivity(ChatPage::class.java)
                    true
                }
                R.id.account -> {
                    startNewActivity(AccountPage::class.java)
                    true
                }
                else -> false
            }
        }
        fetchProductData()
    }





    // Fetch product data from the server
    private fun fetchProductData() {
        // Dummy product data
        val dummyProducts = listOf(
            ProductDataClass(
                id = 101,
                imageResource = R.drawable.wavepadlogo,
                title = "Dummy Product 1",
                author = "Author 1",
                categories = "Fiction",
                price = "$50",
                description = "Description 1"
            ),
            ProductDataClass(
                id = 102,
                imageResource = R.drawable.wavepadlogo,
                title = "Dummy Product 2",
                author = "Author 2",
                categories = "Fantasy",
                price = "$60",
                description = "Description 2"
            ),
            ProductDataClass(
                id = 103,
                imageResource = R.drawable.wavepadlogo,
                title = "Dummy Product 3",
                author = "Author 3",
                categories = "Mystery",
                price = "$70",
                description = "Description 3"
            ),
            ProductDataClass(
                id = 104,
                imageResource = R.drawable.wavepadlogo,
                title = "New Product",
                author = "New Author",
                categories = "Thriller",
                price = "$80",
                description = "New Description"
            )
        )

        // Add dummy products to the productList
        productList.addAll(dummyProducts)

        // Update filteredList
        filteredList = productList.toList()

        // Notify the adapter that the data set has changed
        productAdapter.notifyDataSetChanged()

        // Continue with existing code
        productService.getProducts { products ->
            productList.clear()
            products?.let {
                productList.addAll(it)

                // Update filteredList
                filteredList = productList.toList()

                // Notify the adapter that the data set has changed
                productAdapter.notifyDataSetChanged()
            }
        }
    }








    // Function to add categories dynamically
    private fun addCategories() {
        // List of categories
        val categories = listOf("Fiction", "Fantasy", "Mystery", "Thriller", "Romance", "Science Fiction")

        // Get reference to the LinearLayout
        val categoryLayout = findViewById<LinearLayout>(R.id.categoryLayout)

        // Add categories to the LinearLayout
        categories.forEach { category ->
            val categoryTextView = TextView(this)
            categoryTextView.text = category
            // Set layout parameters
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(16, 0, 16, 0) // Set margins between categories
            categoryTextView.layoutParams = layoutParams
            // Set click listener to handle category selection
            categoryTextView.setOnClickListener {
                // Handle category selection, e.g., display books of selected category
                displayBooksByCategory(category)
            }
            categoryLayout.addView(categoryTextView)
        }
    }


    // Function to display books of a selected category
    private fun displayBooksByCategory(category: String) {
        // Your implementation to display books of the selected category
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Search products"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                productAdapter.filter(newText)
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                startNewActivity(CartPage::class.java)
                true
            }
            R.id.menu -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UPLOAD_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imagePath = data?.getStringExtra("imageFile")
            if (!imagePath.isNullOrEmpty()) {
                productList.add(ProductDataClass(
                    id = productList.size,
                    imageResource = R.drawable.wavepadlogo,
                    title = "New Product",
                    author = "New Author",
                    categories = "New Genre",
                    price = "$100",
                    description = "New Description"
                ))
                productAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun startNewActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }

    companion object {
        private const val UPLOAD_REQUEST_CODE = 123
    }
}
