package com.example.samsung_coursework.data.retrofit

import com.example.samsung_coursework.data.retrofit.dto.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

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

    ): Response<EventsResponseDTO>

    @GET("event-categories/")
    suspend fun getAllCategoriesEvent(): Response<List<CategoryEventDTO>>

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
    ): Response<EventsResponseDTO>

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

    ): Response<EventsResponseDTO>

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

    ): Response <EventsResponseDTO>





    @GET("search/") //Поиск событий, строка
    suspend fun searchBar(
        @Query("lang") language: String = "ru",
        @Query("page_size") size: Int = 10,
        @Query("q") title: String = "Выставка",
        @Query("location") location: String = "msk",
        @Query("ctype") type: String = "event",
        @Query("is_free") free: Boolean = false,

    ): Response <EventsResponseDTO>

    /*
    @GET("search/") //Поиск мест, фильтры
    suspend fun searchPlaces(
        @Query("ctype") type: String = "place",
        @Query("lang") language: String = "ru",
        @Query("page_size") size: Int,
        @Query("location") location: String,
        @Query("is_free") free: Boolean

    ): Response <EventsResponseDTO>
                                 /**TODO добавить новую модель для мест??? **/
     */

    @GET("places/") //Поиск мест, фильтры
    suspend fun searchPlaces(
        @Query("lang") language: String = "ru",
        @Query("page_size") size: Int = 30,
        @Query("location") location: String = "msk",
        @Query("is_free") free: Boolean = true,
        @Query("fields") fields: String = "id,title,short_title," +
                "address,location,timetable,phone,images,description," +
                "body_text,foreign_url,favorites_count,categories",
        @Query("order_by") order: String = "-favorites_count",
        @Query("expand") expand: String = "location",
        @Query("categories") categories: String? = null

    ): Response <SearchedPlaceResponseDTO>

    @GET("place-categories/")
    suspend fun getAllCategoriesPlaces(): Response<List<CategoryPlaceDTO>>

    @GET("events/") //Поиск событий, фильтры
    suspend fun searchEvents(
        @Query("lang") language: String = "ru",
        @Query("page_size") size: Int = 30,
        @Query("location") location: String,
        @Query("is_free") free: Boolean,

        @Query("fields") fields: String = "id,dates,title,short_title," +
                "place,description,body_text,location,categories,age_restriction," +
                "price,is_free,images",
        @Query("actual_since") time: Long = (System.currentTimeMillis() / 1000),
        //@Query("actual_until") actualUntil: Long = (System.currentTimeMillis() / 1000 + 14 * 24 * 60 * 60),
        @Query("order_by") order: String = "-id",
        @Query("expand") expand: String = "place,location,dates",
        @Query("categories") categories: String? = null

    ): Response <EventsResponseDTO>

    @GET("events/")
    suspend fun getFavoriteEvents(
        @Query("ids") ids: String,
        @Query("page_size") pageSize: Int = 100,
        @Query("fields") fields: String = "id,dates,title,short_title," +
                "place,description,body_text,location,categories,age_restriction," +
                "price,is_free,images",
        @Query("expand") expand: String = "place,location"
    ): Response <EventsResponseDTO>

}
