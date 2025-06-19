package com.example.samsung_coursework.domain.use_cases

import com.example.samsung_coursework.domain.ApiRepository
import com.example.samsung_coursework.domain.models.Event

class GetMostPopularEventsUseCase(eventRepository: ApiRepository) {
    private val repository = eventRepository
    suspend fun getMostPopularEvents(code: String = "msk"): List<Event>{
        return repository.getMostPopularEvents(code)
    }
}