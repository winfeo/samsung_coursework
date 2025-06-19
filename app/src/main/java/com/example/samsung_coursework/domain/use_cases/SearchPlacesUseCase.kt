package com.example.samsung_coursework.domain.use_cases

import com.example.samsung_coursework.domain.ApiRepository
import com.example.samsung_coursework.domain.models.SearchedPlace

class SearchPlacesUseCase(apiRepository: ApiRepository) {
    private val repository = apiRepository
    suspend fun searchPlaces(pageSize: Int = 30, location: String = "msk", isFree: Boolean = false,
    orderBy: String? = null): List<SearchedPlace>{
        return repository.searchPlaces(pageSize, location, isFree, orderBy)
    }
}