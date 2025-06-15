package com.example.samsung_coursework.data

import android.util.Log

import com.example.samsung_coursework.data.retrofit.RetrofitClient
import com.example.samsung_coursework.data.retrofit.dto.EventsResponseDTO
import com.example.samsung_coursework.domain.EventRepository
import com.example.samsung_coursework.domain.models.CategoryEvent
import com.example.samsung_coursework.domain.models.CategoryPlace
import com.example.samsung_coursework.domain.models.Event

//Реализ. методов
class EventRepositoryImp(): EventRepository {
    override suspend fun getEvents(code: String): List<Event> {
        return try {
            RetrofitClient.api.getEvents(location = code).body()?.results?.toDomainEvents()?: emptyList()
        } catch (e: Exception) {
            Log.d("Error", "Не удалось загрузить все события")
            emptyList()
        }
    }

    override suspend fun getAllCategoriesEvent(): List<CategoryEvent> {
        return try{
            RetrofitClient.api.getAllCategoriesEvent().body()?.toDomainCategoriesEvent()?: emptyList()
        } catch (e: Exception){
            Log.d("Error", "Не удалось загрузить список категорий событий")
            emptyList()
        }
    }

    override suspend fun getMostPopularEvent(code: String): Event? {
        return try {
            RetrofitClient.api.getMostPopularEvent(location = code).body()?.results?.firstOrNull()?.toDomain()
        } catch (e: Exception) {
            Log.d("Error", "Не удалось загрузить самое популярное метро")
            null
        }
    }

    override suspend fun getFreeEvents(code: String): List<Event>{
        return try {
            RetrofitClient.api.getFreeEvents(location = code).body()?.results?.toDomainEvents()?: emptyList()
        } catch (e: Exception) {
            Log.d("Error", "Не удалось загрузить список бесплат событий")
            emptyList()
        }
    }

    override suspend fun getMostPopularEvents(code: String): List<Event>{
        return try {
            RetrofitClient.api.getMostPopularEvents(location = code).body()?.results?.toDomainEvents()?: emptyList()
        } catch (e: Exception) {
            Log.d("Error", "Не удалось загрузить список самых попул событий")
            emptyList()
        }
    }

    override suspend fun getAllCategoriesPlace(): List<CategoryPlace> {
        return try {
            RetrofitClient.api.getAllCategoriesPlaces().body()?.toDomainCategoriesPlace()?: emptyList()
        } catch (e: Exception) {
            Log.d("Error", "Не удалось загрузить список всех категорий мест")
            emptyList()
        }
    }

    override suspend fun getFavoriteEvents(ids: String): List<Event> {
        return try {
            RetrofitClient.api.getFavoriteEvents(ids = ids).body()?.results?.toDomainEvents()?: emptyList()
        } catch (e: Exception) {
            Log.d("Error", "Любимки не любимки :(((((")
            emptyList()
        }
    }
}