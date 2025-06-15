package com.example.samsung_coursework.ui.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samsung_coursework.data.EventRepositoryImp
import com.example.samsung_coursework.data.FirebaseRepositoryImp
import com.example.samsung_coursework.domain.models.Event
import com.example.samsung_coursework.domain.use_cases.firebase.AddFavoriteEventUseCase
import com.example.samsung_coursework.domain.use_cases.firebase.DeleteFavoriteEventUseCase
import com.example.samsung_coursework.domain.use_cases.firebase.GetFavoriteEventsUseCase
import com.example.samsung_coursework.domain.use_cases.firebase.GetUserDataUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteViewModel: ViewModel() {
    /** TODO Hilt **/
    private val repository = FirebaseRepositoryImp()
    private val eventRepository = EventRepositoryImp()
    private val addFavoriteEventUseCase = AddFavoriteEventUseCase(repository)
    private val deleteFavoriteEventUseCase = DeleteFavoriteEventUseCase(repository)
    private val getUserDataUseCase = GetUserDataUseCase(repository)
    private val getFavoriteEventsUseCase = GetFavoriteEventsUseCase(eventRepository)

    //список событий
    private val _favoriteEvents = MutableLiveData<List<Event>>(emptyList())
    val favoriteEvents: LiveData<List<Event>> = _favoriteEvents

    //проверка для обнов.
    private val _favoriteEventIds = MutableLiveData<Set<Int>>(emptySet())
    val favoriteEventIds: LiveData<Set<Int>> = _favoriteEventIds

    private val _favoriteEventsCount = MutableLiveData<Int>(0)
    val favoriteCount: LiveData<Int> = _favoriteEventsCount

    fun updateFavoriteEvents() {
        viewModelScope.launch {
            try {
                val user = getUserDataUseCase.getUser()
                /** TODO если пользователь не авторизован, то сообщение об этом **/
                if (user != null) {
                    val ids = repository.getFavoriteEventsIds(user.userId)
                    _favoriteEventIds.value = ids.toSet()

                    val events = getFavoriteEventsUseCase.getFavoriteEvents(ids)
                    _favoriteEvents.value = events
                    _favoriteEventsCount.value = events.size
                    Log.d("FavoriteVM", "Favorite event IDs: $ids")
                    Log.d("FavoriteVM", "Favorite events count: ${events.size}")
                }
            } catch (e: Exception) {
                /** TODO обработка ошибок **/
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



}