package com.kanika.newsreaderapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitInstance {
    private const val BASE_URL = "https://newsapi.org/v2/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: NewsAPI by lazy {
        retrofit.create(NewsAPI::class.java)
    }
}
