package com.example.samsung_coursework.domain.models

data class SearchedPlace (
    val id: Int,
    val title: String,
    val shortTitle: String?,
    val address: String?,
    val timetable: String?,
    val phone: String?,
    val bodyText: String?,
    val description: String?,
    val foreignUrl: String?,
    val favoritesCount: Int?,
    val images: List<ImageResource>?,
    val categories: List<String>?,
    val location: String?
)

data class SearchedPlaceResponse(
    val results: List<SearchedPlace>?,
    val count: Int
)
