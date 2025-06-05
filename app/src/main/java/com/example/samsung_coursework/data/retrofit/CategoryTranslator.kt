package com.example.samsung_coursework.data.retrofit

import com.example.samsung_coursework.domain.models.*

//Перевод категорий API
object CategoryTranslator {
    private lateinit var categoryMap: Map<String, String>

    fun initFromList(categories: List<Category>) {
        categoryMap = categories.associate { it.slug to it.name }
    }

    fun translateCategory(slugs: List<String>): String {
        return slugs.map { categoryMap[it] ?: it }.joinToString(", ")
    }
}