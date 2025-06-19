package com.example.samsung_coursework.domain.use_cases

import com.example.samsung_coursework.domain.ApiRepository
import com.example.samsung_coursework.domain.models.Event

class SearchEventsUseCase(apiRepository: ApiRepository) {
    private val repository = apiRepository
    suspend fun searchEvents(pageSize: Int = 30, location: String = "msk", isFree: Boolean = false,
    orderBy: String? = null): List<Event>{
        return repository.searchEvents(pageSize, location, isFree, orderBy)
    }
}