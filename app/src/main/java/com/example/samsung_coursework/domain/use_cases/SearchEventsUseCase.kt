package com.example.samsung_coursework.domain.use_cases

import com.example.samsung_coursework.domain.EventRepository
import com.example.samsung_coursework.domain.SearchRepository
import com.example.samsung_coursework.domain.models.Event

class SearchEventsUseCase(SearchRepository: SearchRepository) {
    private val repository = SearchRepository
    suspend fun searchEvents(pageSize: Int = 30, location: String = "msk", isFree: Boolean = false): List<Event>{
        return repository.searchEvents(pageSize, location, isFree)
    }
}