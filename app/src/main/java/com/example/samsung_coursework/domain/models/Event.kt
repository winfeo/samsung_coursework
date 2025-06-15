package com.example.samsung_coursework.domain.models

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
    val images: List<ImageResource>?,
    val is_favorite: Boolean = false
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
    val startTimeNumber: Long?,
    val endTimeNumber: Long?,
    val startDate: String?,
    val endDate: String?,
    val startTime: String?,
    val endTime: String?,
    val schedules: List<Schedules>?
)


data class Schedules(
    val schedules: List<Int>,
    val startTime: String?,
    val endTime: String?,
)


data class Place(
    val id: Int,
    val title: String,
    val address: String?,
    val subway: String?
)

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