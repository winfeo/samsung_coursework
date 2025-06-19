package com.example.samsung_coursework.domain.use_cases

import com.example.samsung_coursework.domain.ApiRepository
import com.example.samsung_coursework.domain.models.Event

class GetEventsByText(apiRepository: ApiRepository) {
    private val repository = apiRepository
    suspend fun searchEventsByText(query: String): List<Event>{
        return repository.searchByText(query)
    }
}