package com.example.samsung_coursework.domain.models

//import android.location.Location

//Модели, преобраз. из DTO
data class Event(
    val id: Int,
    val dates: List<EventDate>?,
    val title: String,
    val short_title: String?,
    val place: Place?,
    val description: String?,
    val body_text: String?,
    val location: Location?,
    val categories: List<String>,
    val age_restriction: String?,
    val price: String?,
    val is_free: Boolean,
    val images: List<ImageResource>?
)

//Осн. формат ответа
/** TODO нужен ли теперь? **/
data class EventsResponse(
    val results: List<Event>,
    val count: Int,
    val next: String?,
    val previous: String?
)

data class EventDate(
    val startTime: Long?,
    val endTime: Long?
)

// Класс для места проведения
data class Place(
    val id: Int,
    val title: String,
    val address: String?,
    val subway: String?
)

//Место проведения
data class Location(
    val slug: String?,
    val name: String,
    val timezone: String?,
    val coordinates: Coordinates?,
    val language: String?,
    val currency: String?
)

data class Coordinates(
    val lat: Double,
    val lon: Double
)

data class ImageResource(
    val url: String,
    val source: Source?
)

data class Source(
    val name: String?,
    val link: String?
)