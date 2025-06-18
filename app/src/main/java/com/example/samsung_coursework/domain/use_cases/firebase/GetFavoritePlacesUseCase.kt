package com.example.samsung_coursework.domain.use_cases.firebase

import com.example.samsung_coursework.domain.EventRepository
import com.example.samsung_coursework.domain.models.SearchedPlace

class GetFavoritePlacesUseCase(eventRepository: EventRepository) {
    private val repository = eventRepository

    suspend fun getFavoritePlaces(ids: List<Int>): List<SearchedPlace>{
        if(ids.isEmpty()){
            return emptyList()
        }

        val allPlaces = mutableListOf<SearchedPlace>()
        val idsString = ids.joinToString(",")
        val places = repository.getFavoritePlaces(idsString)
        allPlaces.addAll(places)

        return allPlaces
    }
}