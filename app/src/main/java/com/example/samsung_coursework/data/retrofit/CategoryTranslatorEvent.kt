package com.example.samsung_coursework.data.retrofit

import com.example.samsung_coursework.domain.models.*

//Перевод категорий API
object CategoryTranslatorEvent {
    private lateinit var categoryMapEvent: Map<String, String>

    fun initFromList(categories: List<CategoryEvent>) {
        categoryMapEvent = categories.associate { it.slug to it.name }
    }

    fun translateCategory(slugs: List<String>): String {
        return slugs.map { categoryMapEvent[it] ?: it }.joinToString(", ")
    }
}