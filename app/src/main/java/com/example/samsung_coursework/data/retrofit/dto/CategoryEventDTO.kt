package com.example.samsung_coursework.data.retrofit.dto

import com.google.gson.annotations.SerializedName

data class CategoryEventDTO (
    @SerializedName("id") val id: Int,
    @SerializedName("slug") val slug: String,
    @SerializedName("name") val name: String
)