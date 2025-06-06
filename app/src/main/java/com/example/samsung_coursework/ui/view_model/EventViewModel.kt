package com.example.samsung_coursework.ui.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samsung_coursework.data.EventRepositoryImp
import com.example.samsung_coursework.data.retrofit.CategoryTranslator
import com.example.samsung_coursework.domain.models.Event
import com.example.samsung_coursework.domain.use_cases.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class EventViewModel : ViewModel() {
    private val repository = EventRepositoryImp() //создание репозитория
    private val getAllCategoriesUseCase = GetAllCategoriesUseCase(repository)
    private val getEventsUseCase = GetEventsUseCase(repository)
    private val getFreeEventsUseCase = GetFreeEventsUseCase(repository)
    private val getMostPopularEventsUseCase = GetMostPopularEventsUseCase(repository)
    private val getMostPopularEventUseCase = GetMostPopularEventUseCase(repository)

    private val _events = MutableLiveData<List<Event>>() //все события
    private val _freeEvents = MutableLiveData<List<Event>>() //бесплатные события
    private val _mostPopularEvent = MutableLiveData<Event?>() //самое популярное событие
    private val _mostPopularEvents = MutableLiveData<List<Event>>() //самые популярные события
    private val _isLoading = MutableLiveData<Boolean>() //анимаия загрузки
    private val _isPageVisible = MutableLiveData<Boolean>() //видимость домашней страницы

    //переменные для использования в UI
    val events: LiveData<List<Event>> = _events
    val freeEvents: LiveData<List<Event>> = _freeEvents
    val mostPopularEvent: LiveData<Event?> = _mostPopularEvent
    val mostPopularEvents: LiveData<List<Event>> = _mostPopularEvents
    val isLoading: LiveData<Boolean> = _isLoading
    val isPageVisible: LiveData<Boolean> = _isPageVisible

    fun loadEvents(code: String = "msk") {
        viewModelScope.launch { //фоновая задача
            _isLoading.value = true
            _isPageVisible.value = false
            try{
                /**TODO перенести загрузку категорий в другое место?**/
                val categories = getAllCategoriesUseCase.getAllCategories()
                if (categories.isNotEmpty()) {
                    CategoryTranslator.initFromList(categories)
                }

                val popularEvent = async { getMostPopularEventUseCase.getAllCategories(code) }
                val events = async { getEventsUseCase.getEvent(code) }
                val freeEvents = async { getFreeEventsUseCase.getFreeEvents(code) }
                val mostPopularEvents = async { getMostPopularEventsUseCase.getMostPopularEvents(code) }

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
            } finally {
                _isLoading.value = false
                _isPageVisible.value = true
            }
        }
    }
}