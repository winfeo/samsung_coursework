package com.example.samsung_coursework.data

import android.util.Log

import com.example.samsung_coursework.data.retrofit.RetrofitClient
import com.example.samsung_coursework.domain.EventRepository
import com.example.samsung_coursework.domain.models.Category
import com.example.samsung_coursework.domain.models.Event

//Реализ. методов
class EventRepositoryImp(): EventRepository {
    override suspend fun getEvents(): List<Event> {
        return try {
            /**TODO нужно ли здесь преобразовывать из DTO в Model?**/
            RetrofitClient.api.getEvents().body()?.results?.toDomainEvents()?: emptyList()
        } catch (e: Exception) {
            Log.d("Error", "Не удалось загрузить все события")
            emptyList()
        }
    }

    override suspend fun getAllCategories(): List<Category> {
        return try{
            RetrofitClient.api.getAllCategories().body()?.toDomainCategories()?: emptyList()
        } catch (e: Exception){
            Log.d("Error", "Не удалось загрузить список категорий")
            emptyList()
        }
    }

    override suspend fun getMostPopularEvent(): Event? {
        return try {
            RetrofitClient.api.getMostPopularEvent().body()?.results?.firstOrNull()?.toDomain()
        } catch (e: Exception) {
            Log.d("Error", "Не удалось загрузить самое популярное метро")
            null
        }
    }

    override suspend fun getFreeEvents(): List<Event>{
        return try {
            RetrofitClient.api.getFreeEvents().body()?.results?.toDomainEvents()?: emptyList()
        } catch (e: Exception) {
            Log.d("Error", "Не удалось загрузить список бесплат событий")
            emptyList()
        }
    }

    override suspend fun getMostPopularEvents(): List<Event>{
        return try {
            RetrofitClient.api.getMostPopularEvents().body()?.results?.toDomainEvents()?: emptyList()
        } catch (e: Exception) {
            Log.d("Error", "Не удалось загрузить список самых попул событий")
            emptyList()
        }
    }
}