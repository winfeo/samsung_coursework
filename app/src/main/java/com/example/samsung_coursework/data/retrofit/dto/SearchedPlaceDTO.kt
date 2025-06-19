package com.example.samsung_coursework.data.retrofit.dto

import com.google.gson.annotations.SerializedName

//для поиска мест
data class SearchedPlaceDTO (
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("short_title") val shortTitle: String?,
    @SerializedName("address") val address: String?,
    @SerializedName("timetable") val timetable: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("body_text") val bodyText: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("foreign_url") val foreignUrl: String?,
    @SerializedName("favorites_count") val favoritesCount: Int?,
    @SerializedName("images") val images: List<ImageResourceDTO>?,
    @SerializedName("categories") val categories: List<String>,
    @SerializedName("location") val location: String?
)

data class SearchedPlaceResponseDTO(
    @SerializedName("results") val results: List<SearchedPlaceDTO>?,
    @SerializedName("count") val count: Int
)



