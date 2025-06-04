package com.example.samsung_coursework.data

import android.util.Log

import com.example.samsung_coursework.data.retrofit.RetrofitClient
import com.example.samsung_coursework.domain.EventRepository

//Реализ. методов
class EventRepositoryImp(): EventRepository {
    override suspend fun getEvents(): List<Event> {
        return try {
            RetrofitClient.api.getEvents().body()?.results ?: emptyList()
        } catch (e: Exception) {
            Log.d("Error", "Не удалось загрузить все события")
            emptyList()
        }
    }

    suspend fun getAllCategories(): List<Category> {
        return try{
            RetrofitClient.api.getAllCategories().body()?: emptyList()
        } catch (e: Exception){
            Log.d("Error", "Не удалось загрузить категории")
            emptyList()
        }
    }

    suspend fun getMostPopularEvent(): Event? {
        return try {
            RetrofitClient.api.getMostPopularEvent().body()?.results?.firstOrNull()
        } catch (e: Exception) {
            Log.d("Error", "Не удалось загрузить данные в репозиторий")
            null
        }
    }

    suspend fun getFreeEvents(): List<Event>{
        return try {
            RetrofitClient.api.getFreeEvents().body()?.results?: emptyList()
        } catch (e: Exception) {
            Log.d("Error", "Не удалось загрузить бесплатные события")
            emptyList()
        }
    }

    suspend fun getMostPopularEvents(): List<Event>{
        return try {
            RetrofitClient.api.getMostPopularEvents().body()?.results?: emptyList()
        } catch (e: Exception) {
            Log.d("Error", "Не удалось загрузить бесплатные события")
            emptyList()
        }
    }
}