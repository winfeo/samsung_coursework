package com.example.samsung_coursework.domain.use_cases

import com.example.samsung_coursework.domain.EventRepository
import com.example.samsung_coursework.domain.models.Event

class GetFreeEventsUseCase(eventRepository: EventRepository) {
    private val repository = eventRepository
    suspend fun getFreeEvents(code: String = "msk"): List<Event>{
        return repository.getFreeEvents(code)
    }
}