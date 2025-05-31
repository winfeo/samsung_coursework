package com.example.samsung_coursework.models.retrofit

data class Category(
    val id: Int,
    val slug: String,
    val name: String
)

//Перевод категорий от API
object CategoryTranslator {
    private var categoryMap: Map<String, String> = emptyMap()

    fun initFromList(categories: List<Category>) {
        categoryMap = categories.associate { it.slug to it.name }
    }

    fun translateCategory(slugs: List<String>): String {
        return slugs.map { categoryMap[it] ?: it }.joinToString(", ")
    }
}