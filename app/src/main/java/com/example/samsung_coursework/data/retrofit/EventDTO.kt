package com.example.samsung_coursework.data.retrofit

import com.google.gson.annotations.SerializedName
import android.location.Location

//классы DTO, сопаст. с JSON
data class EventDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("dates") val dates: List<EventDateDTO>?,
    @SerializedName("title") val title: String,
    @SerializedName("short_title") val short_title: String?,
    @SerializedName("place") val place: PlaceDTO?,
    @SerializedName("description") val description: String?,
    @SerializedName("body_text") val body_text: String?,
    @SerializedName("location") val location: LocationDTO?,
    @SerializedName("categories") val categories: List<String>,
    @SerializedName("age_restriction") val age_restriction: String?,
    @SerializedName("price") val price: String?,
    @SerializedName("is_free") val is_free: Boolean,
    @SerializedName("images") val images: List<ImageResourceDTO>?
)

//Осн. формат ответа
data class EventsResponseDTO(
    @SerializedName("results")
    val results: List<EventDTO>,

    @SerializedName("count")
    val count: Int,

    @SerializedName("next")
    val next: String?,

    @SerializedName("previous")
    val previous: String?
)

data class EventDateDTO(
    @SerializedName("start") val startTime: Long?,
    @SerializedName("end") val endTime: Long?
)

// Класс для места проведения
data class PlaceDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("address") val address: String?,
    @SerializedName("subway") val subway: String?
)

/**
 * TODO удалить вообще мб этот класс?????
 */
//Место проведения
data class LocationDTO(
    @SerializedName("slug") val slug: String?,
    @SerializedName("name") val name: String,
    @SerializedName("timezone") val timezone: String?,
    @SerializedName("coords") val coordinates: CoordinatesDTO?,
    @SerializedName("language") val language: String?,
    @SerializedName("currency") val currency: String?
)

data class CoordinatesDTO(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double
)

data class ImageResourceDTO(
    @SerializedName("image") val url: String,
    @SerializedName("source") val source: SourceDTO?
)

data class SourceDTO(
    @SerializedName("name") val name: String?,
    @SerializedName("link") val link: String?
)