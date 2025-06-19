package com.example.samsung_coursework.domain.use_cases

import com.example.samsung_coursework.domain.ApiRepository
import com.example.samsung_coursework.domain.models.CategoryPlace

class GetAllCategoriesPlaceUseCase(eventRepository: ApiRepository) {
    private val repository = eventRepository
    suspend fun getAllCategories(): List <CategoryPlace>{
        return repository.getAllCategoriesPlace()
    }
}