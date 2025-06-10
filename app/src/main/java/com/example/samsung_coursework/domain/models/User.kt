package com.example.samsung_coursework.domain.models

data class User(
    val userId: String = "",
    val userEmail: String,
    val userFavoriteEvents: List<Int> = emptyList(),
    val userFavoritePlaces: List<Int> = emptyList()
)
