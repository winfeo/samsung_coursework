package com.example.samsung_coursework.domain.use_cases

import com.example.samsung_coursework.domain.EventRepository
import com.example.samsung_coursework.domain.models.Event

class GetMostPopularEventUseCase(eventRepository: EventRepository) {
    private val repository = eventRepository
    suspend fun getAllCategories(code: String = "msk"): Event?{
        return repository.getMostPopularEvent(code)
    }
}