package com.example.samsung_coursework.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samsung_coursework.models.EventRepository
import com.example.samsung_coursework.models.retrofit.CategoryTranslator
import com.example.samsung_coursework.models.retrofit.Event
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class EventViewModel : ViewModel() {
    private val repository = EventRepository() //создание репозитория
    private val _events = MutableLiveData<List<Event>>() //все события
    private val _freeEvents = MutableLiveData<List<Event>>() //бесплатные события
    private val _mostPopularEvent = MutableLiveData<Event?>() //самое популярное событие
    private val _mostPopularEvents = MutableLiveData<List<Event>>() //самые популярные события

    //переменные для использования в UI
    val events: LiveData<List<Event>> = _events
    val freeEvents: LiveData<List<Event>> = _freeEvents
    val mostPopularEvent: LiveData<Event?> = _mostPopularEvent
    val mostPopularEvents: LiveData<List<Event>> = _mostPopularEvents

    fun loadEvents() {
        viewModelScope.launch { //фоновая задача
            try{
                val categoryResponse = repository.getAllCategories()
                if (categoryResponse.isNotEmpty()) {
                    CategoryTranslator.initFromList(categoryResponse)
                }

                val popularEvent = async { repository.getMostPopularEvent() }
                val events = async { repository.getEvents() }
                val freeEvents = async { repository.getFreeEvents() }
                val mostPopularEvents = async { repository.getMostPopularEvents() }

                _mostPopularEvent.value = popularEvent.await()
                _events.value = events.await()
                _freeEvents.value = freeEvents.await()
                _mostPopularEvents.value = mostPopularEvents.await()

            }catch (e: Exception){
                Log.d("Error", "Не удалось загрузить данные")
                _mostPopularEvent.value = null
                _events.value = emptyList()
                _freeEvents.value = emptyList()
                _mostPopularEvents.value = emptyList()
            }
        }
    }
}