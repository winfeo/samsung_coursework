package com.example.samsung_coursework.data

import android.util.Log
import com.example.samsung_coursework.data.retrofit.RetrofitClient
import com.example.samsung_coursework.domain.SearchRepository
import com.example.samsung_coursework.domain.models.Event

/** TODO временное решение, заменить в последстивие на Hilt (НЕ ЗАБЫТЬ ПРО USE-CASE-Ы, поменять репозиторий там)**/
class SearchRepositoryImp(): SearchRepository {
    override suspend fun searchEvents(pageSize: Int, location: String, isFree: Boolean): List<Event>{
        return try{
            RetrofitClient.api.searchEvents(size = pageSize, location = location, free = isFree).body()?.results?.toDomainEvents()?: emptyList()
        } catch (e: Exception){
            Log.d("Error", "Не удалось найти события")
            emptyList()
        }
    }
}