package com.example.samsung_coursework.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samsung_coursework.data.SearchRepositoryImp
import com.example.samsung_coursework.domain.models.Event
import com.example.samsung_coursework.domain.use_cases.SearchEventsUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {
    private val repository = SearchRepositoryImp()
    private val searchEventsUseCase = SearchEventsUseCase(repository)

    private val _isButtonEventsPressed = MutableLiveData <Boolean>(false)
    private val _isLoading = MutableLiveData <Boolean>()
    private val _events = MutableLiveData <List<Event>>()//фильтр событий

    var isButtonEventsPressed = _isButtonEventsPressed
    val loading: LiveData <Boolean> = _isLoading
    val events: LiveData <List<Event>> = _events

    fun searchEvents(pageSize: Int, location: String, isFree: Boolean){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val events = async { searchEventsUseCase.searchEvents(pageSize, location, isFree) }
                _events.value = events.await()
            } catch (e: Exception) {
                _events.value = emptyList()
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun changeEventColor(){
        _isButtonEventsPressed.value = !(_isButtonEventsPressed.value?: false)
    }
}