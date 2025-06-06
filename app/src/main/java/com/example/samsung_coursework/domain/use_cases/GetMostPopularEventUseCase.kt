package com.example.samsung_coursework.domain.use_cases

import com.example.samsung_coursework.domain.EventRepository
import com.example.samsung_coursework.domain.models.Event

class GetMostPopularEventUseCase(EventRepository: EventRepository) {
    private val repository = EventRepository
    suspend fun getAllCategories(code: String = "msk"): Event?{
        return repository.getMostPopularEvent(code)
    }
}