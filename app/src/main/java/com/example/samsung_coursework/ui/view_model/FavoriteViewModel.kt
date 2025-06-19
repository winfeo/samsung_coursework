package com.example.samsung_coursework.ui.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samsung_coursework.data.ApiRepositoryImp
import com.example.samsung_coursework.data.FirebaseRepositoryImp
import com.example.samsung_coursework.domain.models.Event
import com.example.samsung_coursework.domain.models.SearchedPlace
import com.example.samsung_coursework.domain.use_cases.firebase.*
import kotlinx.coroutines.launch

class FavoriteViewModel: ViewModel() {
    private val repository = FirebaseRepositoryImp()
    private val eventRepository = ApiRepositoryImp()
    private val getUserDataUseCase = GetUserDataUseCase(repository)

    private val addFavoriteEventUseCase = AddFavoriteEventUseCase(repository)
    private val deleteFavoriteEventUseCase = DeleteFavoriteEventUseCase(repository)
    private val getFavoriteEventsUseCase = GetFavoriteEventsUseCase(eventRepository)

    private val addFavoritePlaceUseCase = AddFavoritePlaceUseCase(repository)
    private val deleteFavoritePlaceUseCase = DeleteFavoritePlaceUseCase(repository)
    private val getFavoritePlacesUseCase = GetFavoritePlacesUseCase(eventRepository)

    private val _favoriteEvents = MutableLiveData<List<Event>>(emptyList())
    val favoriteEvents: LiveData<List<Event>> = _favoriteEvents

    //событ
    private val _favoritePlaces = MutableLiveData<List<SearchedPlace>>(emptyList())
    val favoritePlaces: LiveData<List<SearchedPlace>> = _favoritePlaces

    //проверка для обнов.
    private val _favoriteEventIds = MutableLiveData<Set<Int>>(emptySet())
    val favoriteEventIds: LiveData<Set<Int>> = _favoriteEventIds

    private val _favoritePlaceIds = MutableLiveData<Set<Int>>(emptySet())
    val favoritePlaceIds: LiveData<Set<Int>> = _favoritePlaceIds

    private val _favoriteEventsCount = MutableLiveData<Int>(0)
    val favoriteCount: LiveData<Int> = _favoriteEventsCount

    fun updateFavoriteEvents() {
        viewModelScope.launch {
            try {
                val user = getUserDataUseCase.getUser()
                if (user != null) {
                    val ids = repository.getFavoriteEventsIds(user.userId)
                    _favoriteEventIds.value = ids.toSet()

                    val events = getFavoriteEventsUseCase.getFavoriteEvents(ids)
                    _favoriteEvents.value = events
                    _favoriteEventsCount.value = events.size
                    //Log.d("FavoriteVM", "Айдишники: $ids")
                    //Log.d("FavoriteVM", "Количество: ${events.size}")
                }
            } catch (e: Exception) {
                Log.d("FavoriteVM", "Ошибка")
            }
        }
    }

    fun addFavoriteEvent(eventId: Int) {
        viewModelScope.launch {
            try {
                val user = getUserDataUseCase.getUser()
                if (user != null) {
                    val currentIds = _favoriteEventIds.value?.toMutableSet() ?: mutableSetOf()
                    currentIds.add(eventId)
                    _favoriteEventIds.value = currentIds

                    addFavoriteEventUseCase.add(eventId)

                    updateFavoriteEvents()
                }
            } catch (e: Exception) {
                val currentIds = _favoriteEventIds.value?.toMutableSet() ?: mutableSetOf()
                currentIds.remove(eventId)
                _favoriteEventIds.value = currentIds
            }
        }
    }

    fun deleteFavoriteEvent(eventId: Int) {
        viewModelScope.launch {
            try {
                val user = getUserDataUseCase.getUser()
                if (user != null) {
                    val currentIds = _favoriteEventIds.value?.toMutableSet() ?: mutableSetOf()
                    currentIds.remove(eventId)
                    _favoriteEventIds.value = currentIds

                    deleteFavoriteEventUseCase.delete(eventId)

                    updateFavoriteEvents()
                }
            } catch (e: Exception) {
                val currentIds = _favoriteEventIds.value?.toMutableSet() ?: mutableSetOf()
                currentIds.add(eventId)
                _favoriteEventIds.value = currentIds
            }
        }
    }

    fun isFavorite(eventId: Int): Boolean {
        return _favoriteEventIds.value?.contains(eventId) ?: false
    }







    fun updateFavoritePlaces() {
        viewModelScope.launch {
            try {
                val user = getUserDataUseCase.getUser()
                if (user != null) {
                    val ids = repository.getFavoritePlaceIds(user.userId)
                    _favoritePlaceIds.value = ids.toSet()

                    val places = getFavoritePlacesUseCase.getFavoritePlaces(ids)
                    _favoritePlaces.value = places
                }
            } catch (e: Exception) {
                Log.d("FavoriteVM", "Ошибка")
            }
        }
    }

    fun addFavoritePlace(placeId: Int) {
        viewModelScope.launch {
            try {
                val user = getUserDataUseCase.getUser()
                if (user != null) {
                    addFavoritePlaceUseCase.add(placeId)
                    updateFavoritePlaces()
                }
            } catch (e: Exception) {
                Log.d("FavoriteVM", "Ошибка")
            }
        }
    }

    fun deleteFavoritePlace(placeId: Int) {
        viewModelScope.launch {
            try {
                val user = getUserDataUseCase.getUser()
                if (user != null) {
                    deleteFavoritePlaceUseCase.delete(placeId)
                    updateFavoritePlaces()
                }
            } catch (e: Exception) {
                Log.d("FavoriteVM", "Ошибка")
            }
        }
    }

    fun isFavoritePlace(placeId: Int): Boolean {
        return _favoritePlaceIds.value?.contains(placeId) ?: false
    }



}