package com.example.samsung_coursework.data

import com.example.samsung_coursework.data.retrofit.dto.*
import com.example.samsung_coursework.domain.models.*

//Ивент маппинг
fun EventDTO.toDomain(): Event{
    return Event(
        id = id,
        dates = dates?.map { it.toDomain() },
        title = title,
        short_title = short_title,
        place = place?.toDomain(),
        description = description,
        body_text = body_text,
        location = location?.toDomain(),
        categories = categories,
        age_restriction = age_restriction,
        price = price,
        is_free = is_free,
        images = images?.map { it.toDomain() }
    )
}

//типо Response-а
fun List<EventDTO>.toDomainEvents(): List<Event>{
    return map { it.toDomain() }
}

fun EventDateDTO.toDomain(): EventDate{
    return EventDate(
        startTimeNumber = startTimeNumber,
        endTimeNumber = endTimeNumber,
        startDate = startDate,
        endDate = endDate,
        startTime = startTime,
        endTime = endTime,
        schedules = schedules?.map { it.toDomain() }
    )
}

fun SchedulesDTO.toDomain(): Schedules{
    return Schedules(
        schedules = schedules,
        startTime = startTime,
        endTime = endTime
    )
}


fun PlaceDTO.toDomain(): Place{
    return Place(
        id = id,
        title = title,
        address = address,
        subway = subway
    )
}

fun LocationDTO.toDomain(): Location{
    return Location(
        slug = slug,
        name = name,
        timezone = timezone,
        coordinates = coordinates?.toDomain(),
        language = language,
        currency = currency
    )
}

fun CoordinatesDTO.toDomain(): Coordinates{
    return Coordinates(
        lat = lat,
        lon = lon
    )
}

fun ImageResourceDTO.toDomain(): ImageResource{
    return ImageResource(
        url = url,
        source = source?.toDomain()
    )
}

fun SourceDTO.toDomain(): Source{
    return Source(
        name = name,
        link = link
    )
}


//Маппинг категорий
fun List<CategoryDTO>.toDomainCategories(): List<Category>{
    return map { it.toDomain() }
}

fun CategoryDTO.toDomain(): Category{
    return Category(
        id = id,
        slug = slug,
        name = name
    )
}




/** TODO добавить маппинг мест сюда + метод в репозиторий для получения мест + usecase-ы **/
//Маппинг мест
fun List<SearchedPlaceDTO>.toDomainPlaces(): List <SearchedPlace>{
    return map { it.toDomain() }
}

fun SearchedPlaceDTO.toDomain(): SearchedPlace{
    return SearchedPlace(
        id = id,
        title = title,
        shortTitle = shortTitle,
        address = address,
        timetable = timetable,
        phone = phone,
        bodyText = bodyText,
        description = description,
        foreignUrl = foreignUrl,
        favoritesCount = favoritesCount,
        images = images?.map { it.toDomain() },
        categories = categories,
        location = location
    )
}




