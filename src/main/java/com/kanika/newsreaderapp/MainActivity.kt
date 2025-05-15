package com.kanika.newsreaderapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.newsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Make the API call to fetch news
        lifecycleScope.launch {
            try {
                // Fetch top headlines using Retrofit API
                val response = RetrofitInstance.api.getTopHeadlines("in", "91aa06b58a484fd7992befa70c804488")

                Log.d("MainActivity", "Articles size: ${response.body()?.articles?.size}")

                if (response.isSuccessful) {
                    // Check if the response contains articles
                    val articles = response.body()?.articles
                    if (articles != null && articles.isNotEmpty()) {
                        // Set the adapter with the fetched articles
                        recyclerView.adapter = NewsAdapter(articles)
                    } else {
                        Toast.makeText(this@MainActivity, "No articles found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Handle network errors or any other exceptions
                e.printStackTrace()
                Toast.makeText(this@MainActivity, "Failed to load news", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
