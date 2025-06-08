package com.example.samsung_coursework.domain

import com.example.samsung_coursework.domain.models.*

/** TODO Временное решение, заменить на Hilt и использовать один репозиторий **/
/** TODO Добавить параметр @Query("order_by") для реализации выбора сортировки на основании чего **/
interface SearchRepository {
    suspend fun searchEvents(pageSize: Int = 30, location: String = "msk", isFree: Boolean = false): List<Event>
    suspend fun searchPlaces(pageSize: Int = 30, location: String = "msk", isFree: Boolean = false): List<SearchedPlace>
}