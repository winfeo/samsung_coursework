package com.example.samsung_coursework.data.retrofit.dto

data class UserDTO(
    val userId: String,
    val userEmail: String,
    val userFavoriteEvents: List<Int>,
    val userFavoritePlaces: List<Int>
)
