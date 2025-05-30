package com.example.samsung_coursework.models

import android.util.Log
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
}