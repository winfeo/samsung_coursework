package com.example.samsung_coursework.domain

import com.example.samsung_coursework.domain.models.CategoryEvent
import com.example.samsung_coursework.domain.models.CategoryPlace
import com.example.samsung_coursework.domain.models.Event
import com.example.samsung_coursework.domain.models.SearchedPlace

//Интерфейсик
interface ApiRepository {
    suspend fun getEvents(code: String = "msk"): List<Event>
    suspend fun getAllCategoriesEvent(): List<CategoryEvent>
    suspend fun getMostPopularEvent(code: String = "msk"): Event?
    suspend fun getFreeEvents(code: String = "msk"): List<Event>
    suspend fun getMostPopularEvents(code: String = "msk"): List<Event>

    suspend fun getAllCategoriesPlace(): List<CategoryPlace>

    suspend fun getFavoriteEvents(ids: String): List<Event>
    suspend fun getFavoritePlaces(ids: String): List<SearchedPlace>

    suspend fun searchEvents(pageSize: Int = 30, location: String = "msk", isFree: Boolean = false,
                             orderBy: String? = null): List<Event>
    suspend fun searchPlaces(pageSize: Int = 30, location: String = "msk",
                             isFree: Boolean = false, orderBy: String? = null): List<SearchedPlace>

    suspend fun searchByText(query: String): List<Event>
    suspend fun searchByTextPlace(query: String): List<SearchedPlace>
}