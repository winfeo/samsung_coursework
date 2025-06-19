package com.example.samsung_coursework.data

import android.util.Log

import com.example.samsung_coursework.data.retrofit.RetrofitClient
import com.example.samsung_coursework.domain.ApiRepository
import com.example.samsung_coursework.domain.models.CategoryEvent
import com.example.samsung_coursework.domain.models.CategoryPlace
import com.example.samsung_coursework.domain.models.Event
import com.example.samsung_coursework.domain.models.SearchedPlace

//Реализ. методов
class ApiRepositoryImp(): ApiRepository {
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

    override suspend fun getFavoritePlaces(ids: String): List<SearchedPlace> {
        return try {
            RetrofitClient.api.getFavoritePlaces(ids = ids).body()?.results?.toDomainPlaces() ?: emptyList()
        } catch (e: Exception) {
            Log.d("Error", "Любимки не любимки, места :(((((")
            emptyList()
        }
    }


    override suspend fun searchEvents(pageSize: Int, location: String, isFree: Boolean, orderBy: String?): List<Event>{
        return try{
            RetrofitClient.api.searchEvents(size = pageSize, location = location, free = isFree, order = orderBy).body()?.results?.toDomainEvents()?: emptyList()
        } catch (e: Exception){
            Log.d("Error", "Не удалось найти события")
            emptyList()
        }
    }

    override suspend fun searchPlaces(pageSize: Int, location: String, isFree: Boolean, orderBy: String?): List<SearchedPlace> {
        return try{
            RetrofitClient.api.searchPlaces(size = pageSize, location = location, free = isFree, order = orderBy).body()?.results?.toDomainPlaces()?: emptyList()
        } catch (e: Exception){
            Log.d("Error", "Не удалось найти места")
            emptyList()
        }
    }

    override suspend fun searchByText(query: String): List<Event> {
        return try{
            RetrofitClient.api.searchBarEvents(title = query).body()?.results?.toDomainEvents()?: emptyList()
        }
        catch (e: Exception){
            Log.d("Error", "Не удалось найти места по тексту")
            emptyList()
        }

    }

    override suspend fun searchByTextPlace(query: String): List<SearchedPlace> {
        return try{
            RetrofitClient.api.searchBarPlaces(title = query).body()?.results?.toDomainPlaces()?: emptyList()
        }
        catch (e: Exception){
            Log.d("Error", "Не удалось найти места по тексту")
            emptyList()
        }

    }
}