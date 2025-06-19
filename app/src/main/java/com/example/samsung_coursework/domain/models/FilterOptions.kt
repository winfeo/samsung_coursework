package com.example.samsung_coursework.domain.models

data class FilterOptions(
    val searchType: String? = null,
    val location: String? = null,
    val isFree: Boolean? = null,
    val pageSize: Int? = null,
    val orderBy: String? = null,
)
