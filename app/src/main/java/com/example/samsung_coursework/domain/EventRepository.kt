package com.example.samsung_coursework.domain

import com.example.samsung_coursework.domain.models.Category
import com.example.samsung_coursework.domain.models.Event

//Интерфейсик
interface EventRepository {
    suspend fun getEvents(code: String = "msk"): List<Event>
    suspend fun getAllCategories(): List<Category>
    suspend fun getMostPopularEvent(code: String = "msk"): Event?
    suspend fun getFreeEvents(code: String = "msk"): List<Event>
    suspend fun getMostPopularEvents(code: String = "msk"): List<Event>
}