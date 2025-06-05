package com.example.samsung_coursework.data.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://kudago.com/public-api/v1.4/"

    val gson = GsonBuilder()
        .registerTypeAdapter(String::class.java, HtmlStrippingAdapter())
        .create()

    val api: KudaGoApiService by lazy {//1-ое обращ.
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
            .create(KudaGoApiService::class.java)
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain -> //текущ. HTTP-запрос
                val request = chain.request().newBuilder()
                    .header("User-Agent", "YourApp/1.0 (Android; contact@example.com)")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().apply { //логи
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
}