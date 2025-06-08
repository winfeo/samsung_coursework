package com.example.samsung_coursework.domain.use_cases

import com.example.samsung_coursework.domain.SearchRepository
import com.example.samsung_coursework.domain.models.SearchedPlace

class SearchPlacesUseCase(searchRepository: SearchRepository) {
    private val repository = searchRepository
    suspend fun searchPlaces(pageSize: Int = 30, location: String = "msk", isFree: Boolean = false): List<SearchedPlace>{
        return repository.searchPlaces(pageSize, location, isFree)
    }
}