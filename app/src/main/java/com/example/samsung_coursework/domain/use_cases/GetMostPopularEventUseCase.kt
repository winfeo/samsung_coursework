package com.example.samsung_coursework.domain.use_cases

import com.example.samsung_coursework.domain.ApiRepository
import com.example.samsung_coursework.domain.models.Event

class GetMostPopularEventUseCase(apiRepository: ApiRepository) {
    private val repository = apiRepository
    suspend fun getAllCategories(code: String = "msk"): Event?{
        return repository.getMostPopularEvent(code)
    }
}