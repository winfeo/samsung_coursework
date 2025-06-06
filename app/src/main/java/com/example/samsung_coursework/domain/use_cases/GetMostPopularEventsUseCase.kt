package com.example.samsung_coursework.domain.use_cases

import com.example.samsung_coursework.domain.EventRepository
import com.example.samsung_coursework.domain.models.Event

class GetMostPopularEventsUseCase(EventRepository: EventRepository) {
    private val repository = EventRepository
    suspend fun getMostPopularEvents(code: String = "msk"): List<Event>{
        return repository.getMostPopularEvents(code)
    }
}