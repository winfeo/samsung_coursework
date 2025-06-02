package com.example.samsung_coursework.models.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//Интерфейс, описывающий доступные методы API
interface KudaGoApiService {
    @GET("events/") //получение списка всех событий
    suspend fun getEvents(
        @Query("lang") language: String = "ru",
        @Query("page_size") size: Int = 10,
        @Query("fields") fields: String = "id,dates,title,short_title," +
                "place,description,body_text,location,categories,age_restriction," +
                "price,is_free,images",
        @Query("location") location: String = "msk",
        @Query("actual_since") time: Long = (System.currentTimeMillis() / 1000),
        //@Query("actual_until") actualUntil: Long = (System.currentTimeMillis() / 1000 + 14 * 24 * 60 * 60),
        @Query("order_by") order: String = "-id",
        @Query("expand") expand: String = "place,location,dates"

    ): Response<EventsResponse>

    @GET("event-categories/") //получение списка всех категорий сайта
    suspend fun getAllCategories(): Response<List<Category>>

    @GET("events") //получение самого популярного события
    suspend fun getMostPopularEvent(
        @Query("lang") language: String = "ru",
        @Query("page_size") size: Int = 1,
        @Query("fields") fields: String = "id,dates,title,short_title," +
                "place,description,body_text,location,categories,age_restriction," +
                "price,is_free,images",
        @Query("location") location: String = "msk",
        @Query("actual_since") time: Long = (System.currentTimeMillis() / 1000),
        @Query("order_by") order: String = "-favorites_count",
        @Query("expand") expand: String = "place,location,dates"
    ): Response<EventsResponse>

    @GET("events/") //получение списка бесплатных событий
    suspend fun getFreeEvents(
        @Query("lang") language: String = "ru",
        @Query("page_size") size: Int = 10,
        @Query("fields") fields: String = "id,dates,title,short_title," +
                "place,description,body_text,location,categories,age_restriction," +
                "price,is_free,images",
        @Query("location") location: String = "msk",
        @Query("actual_since") time: Long = (System.currentTimeMillis() / 1000),
        //@Query("actual_until") actualUntil: Long = (System.currentTimeMillis() / 1000 + 14 * 24 * 60 * 60),
        @Query("order_by") order: String = "-id",
        @Query("is_free") free: String = "true",
        @Query("expand") expand: String = "place,location,dates"

    ): Response<EventsResponse>

    @GET("events/") //Получение списка самых популярных событий
    suspend fun getMostPopularEvents(
        @Query("lang") language: String = "ru",
        @Query("page_size") size: Int = 10,
        @Query("fields") fields: String = "id,dates,title,short_title," +
                "place,description,body_text,location,categories,age_restriction," +
                "price,is_free,images",
        @Query("location") location: String = "msk",
        @Query("actual_since") time: Long = (System.currentTimeMillis() / 1000),
        @Query("order_by") order: String = "-favorites_count",
        @Query("expand") expand: String = "place,location,dates"

    ): Response <EventsResponse>

}
