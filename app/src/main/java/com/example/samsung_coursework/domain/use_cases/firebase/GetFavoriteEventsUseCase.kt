package com.example.samsung_coursework.domain.use_cases.firebase

import com.example.samsung_coursework.domain.ApiRepository
import com.example.samsung_coursework.domain.models.Event

class GetFavoriteEventsUseCase(eventRepository: ApiRepository) {
    private val repository = eventRepository
    suspend fun getFavoriteEvents(ids: List<Int>): List<Event>{
        if(ids.isEmpty()){
            return emptyList()
        }

        val allEvents = mutableListOf<Event>()
        val idsString = ids.joinToString(",")
        val events = repository.getFavoriteEvents(idsString)
        allEvents.addAll(events)

        return allEvents
    }
}