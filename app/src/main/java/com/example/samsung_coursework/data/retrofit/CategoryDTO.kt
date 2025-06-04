package com.example.samsung_coursework.data.retrofit

import com.google.gson.annotations.SerializedName

data class CategoryDTO (
    @SerializedName("id") val id: Int,
    @SerializedName("slug") val slug: String,
    @SerializedName("name") val name: String
)