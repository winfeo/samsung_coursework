package com.example.samsung_coursework.domain.use_cases

import com.example.samsung_coursework.domain.EventRepository
import com.example.samsung_coursework.domain.models.Category

class GetAllCategoriesUseCase(EventRepository: EventRepository){
    private val repository = EventRepository
    suspend fun getAllCategories(): List<Category>{
        return repository.getAllCategories()
    }
}