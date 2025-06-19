package com.example.samsung_coursework.domain.use_cases

import com.example.samsung_coursework.domain.ApiRepository
import com.example.samsung_coursework.domain.models.CategoryEvent

class GetAllCategoriesEventUseCase(eventRepository: ApiRepository){
    private val repository = eventRepository
    suspend fun getAllCategories(): List<CategoryEvent>{
        return repository.getAllCategoriesEvent()
    }
}