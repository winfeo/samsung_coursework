package com.example.samsung_coursework.domain

import com.example.samsung_coursework.domain.models.Event

/** TODO Временное решение, заменить на Hilt и использовать один репозиторий **/
interface SearchRepository {
    suspend fun searchEvents(pageSize: Int = 30, location: String = "msk", isFree: Boolean = false): List<Event>
    //suspend fun searchPlaces(pageSize: Int = 30, location: String = "msk", isFree: Boolean = false)
}