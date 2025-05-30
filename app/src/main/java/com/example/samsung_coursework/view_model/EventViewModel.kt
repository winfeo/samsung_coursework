package com.example.samsung_coursework.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope // Добавьте этот импорт
import com.example.samsung_coursework.models.EventRepository
import com.example.samsung_coursework.models.retrofit.Event
import kotlinx.coroutines.launch // Добавьте этот импорт

class EventViewModel : ViewModel() {
    private val repository = EventRepository() //создание репозитория
    private val _events = MutableLiveData<List<Event>>() //изменяемое состояние
    val events: LiveData<List<Event>> = _events //переменная для использования в UI

    fun loadEvents() {
        viewModelScope.launch { //фоновая задача
            try {
                _events.value = repository.getEvents() //загружаем данные из репозитория
            } catch (e: Exception) {
                // Обработка ошибок
                _events.value = emptyList()
            }
        }
    }
}