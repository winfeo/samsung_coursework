package com.example.samsung_coursework.domain.use_cases

import com.example.samsung_coursework.domain.EventRepository
import com.example.samsung_coursework.domain.models.Event

class GetEventsUseCase(eventRepository: EventRepository) {
    private val repository = eventRepository
    suspend fun getEvent(code: String = "msk"): List<Event>{
        return repository.getEvents(code)
    }
}