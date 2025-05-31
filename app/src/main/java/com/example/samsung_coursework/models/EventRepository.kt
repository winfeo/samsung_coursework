package com.example.samsung_coursework.models

import android.util.Log
import com.example.samsung_coursework.models.retrofit.Category
import com.example.samsung_coursework.models.retrofit.Event
import com.example.samsung_coursework.models.retrofit.RetrofitClient

//Отвечает за получение данных
class EventRepository {
    suspend fun getEvents(): List<Event> {
        return try {
            RetrofitClient.api.getEvents().body()?.results ?: emptyList()
        } catch (e: Exception) {
            Log.d("Error", "Не удалось загрузить данные в репозиторий")
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
}