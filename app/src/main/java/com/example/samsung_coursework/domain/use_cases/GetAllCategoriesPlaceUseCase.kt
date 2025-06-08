package com.example.samsung_coursework.domain.use_cases

import com.example.samsung_coursework.domain.EventRepository
import com.example.samsung_coursework.domain.models.CategoryPlace

class GetAllCategoriesPlaceUseCase(eventRepository: EventRepository) {
    private val repository = eventRepository
    suspend fun getAllCategories(): List <CategoryPlace>{
        return repository.getAllCategoriesPlace()
    }
}