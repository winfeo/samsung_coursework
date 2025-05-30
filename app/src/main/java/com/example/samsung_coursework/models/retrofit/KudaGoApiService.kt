package com.example.samsung_coursework.models.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//Интерфейс, описывающий доступные методы API
interface KudaGoApiService {
    @GET("events/") //получение списка всех событий
    suspend fun getEvents(
        @Query("lang") language: String = "ru",
        @Query("page") page: Int = 1,
        @Query("page_size") size: Int = 3,
        @Query("fields") fields: String = "id,title,short_title," +
                "place,description,body_text,location,categories,age_restriction," +
                "price,is_free",
        @Query("order_by") order: String = "dates",
        //@Query("location") location: String = "msk",

    ): Response<EventsResponse>
}
