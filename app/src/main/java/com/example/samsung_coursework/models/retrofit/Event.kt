package com.example.samsung_coursework.models.retrofit

import com.google.gson.annotations.SerializedName

//Структуры данных (преобразование JSON-кода в объекты)
data class Event(
    val id: Int,
    val dates: String?, //List<EventDate>
    val title: String,
    val short_title: String?,
    val place: String?, //Place?
    val description: String?,
    val body_text : String?,
    val location: String,
    val categories: String,
    val age_restriction: String,
    val price: String?,
    val is_free: String

    //val images: List<EventImage>?
)

data class EventsResponse(
    @SerializedName("results")
    val results: List<Event>,

    @SerializedName("count")
    val count: Int,

    @SerializedName("next")
    val next: String?,

    @SerializedName("previous")
    val previous: String?
)

// Класс для дат события
data class EventDate(
    @SerializedName("start") val startTime: Long,
    @SerializedName("end") val endTime: Long?
)

// Класс для места проведения
data class Place(
    val id: Int,
    val title: String,
    val address: String?,
    @SerializedName("coords") val coordinates: Coordinates?
)

// Координаты места
data class Coordinates(
    val lat: Double,
    val lon: Double
)

// Класс для изображений
data class EventImage(
    val image: String,
    val source: Source?
)

// Источник изображения
data class Source(
    val name: String?,
    val link: String?
)