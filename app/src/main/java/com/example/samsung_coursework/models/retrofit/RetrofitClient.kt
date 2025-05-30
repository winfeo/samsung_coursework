package com.example.samsung_coursework.models.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Создание API-клиента
object RetrofitClient {
    private const val BASE_URL = "https://kudago.com/public-api/v1.4/"

    val api: KudaGoApiService by lazy { //будет создан только при первом обращении
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(KudaGoApiService::class.java)
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain -> //chain - текущий HTTP-запрос
                val request = chain.request().newBuilder()
                    .header("User-Agent", "YourApp/1.0 (Android; contact@example.com)")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().apply { //логи для просмотра
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
}