package com.example.samsung_coursework.domain.use_cases

import com.example.samsung_coursework.domain.EventRepository
import com.example.samsung_coursework.domain.models.Event

class GetEventsUseCase(EventRepository: EventRepository) {
    private val repository = EventRepository
    suspend fun getEvent(): List<Event>{
        return repository.getEvents()
    }
}