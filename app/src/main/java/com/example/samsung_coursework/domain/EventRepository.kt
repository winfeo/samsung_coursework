package com.example.samsung_coursework.domain

import com.example.samsung_coursework.domain.models.Category
import com.example.samsung_coursework.domain.models.Event

//Интерфейсик
interface EventRepository {
    suspend fun getEvents(): List<Event>
    suspend fun getAllCategories(): List<Category>
    suspend fun getMostPopularEvent(): Event?
    suspend fun getFreeEvents(): List<Event>
    suspend fun getMostPopularEvents(): List<Event>
}