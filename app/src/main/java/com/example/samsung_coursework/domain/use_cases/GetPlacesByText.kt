package com.example.samsung_coursework.domain.use_cases

import com.example.samsung_coursework.domain.ApiRepository
import com.example.samsung_coursework.domain.models.Event
import com.example.samsung_coursework.domain.models.SearchedPlace

class GetPlacesByText(apiRepository: ApiRepository) {
    private val repository = apiRepository
    suspend fun searchPlacesByText(query: String): List<SearchedPlace>{
        return repository.searchByTextPlace(query)
    }
}