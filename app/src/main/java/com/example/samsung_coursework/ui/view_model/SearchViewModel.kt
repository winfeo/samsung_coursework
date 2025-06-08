package com.example.samsung_coursework.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samsung_coursework.data.SearchRepositoryImp
import com.example.samsung_coursework.domain.models.Event
import com.example.samsung_coursework.domain.models.SearchedPlace
import com.example.samsung_coursework.domain.use_cases.SearchEventsUseCase
import com.example.samsung_coursework.domain.use_cases.SearchPlacesUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {
    private val repository = SearchRepositoryImp()
    private val searchEventsUseCase = SearchEventsUseCase(repository)
    private val searchPlacesUseCase = SearchPlacesUseCase(repository)

    private val _isButtonEventsPressed = MutableLiveData <Boolean>(false)
    private val _isButtonPlacesPressed = MutableLiveData <Boolean>(false)
    private val _isButtonNewsPressed = MutableLiveData <Boolean>(false)
    private val _isLoading = MutableLiveData <Boolean>()
    private val _events = MutableLiveData <List<Event>>()//фильтр событий
    private val _places = MutableLiveData <List<SearchedPlace>>()//места

    var isButtonEventsPressed: LiveData <Boolean> = _isButtonEventsPressed
    var isButtonPlacesPressed: LiveData <Boolean> = _isButtonPlacesPressed
    var isButtonNewsPressed: LiveData <Boolean> = _isButtonNewsPressed
    val loading: LiveData <Boolean> = _isLoading
    val events: LiveData <List<Event>> = _events
    val places: LiveData <List<SearchedPlace>> = _places

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

    fun searchPlaces(pageSize: Int, location: String, isFree: Boolean){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val places = async { searchPlacesUseCase.searchPlaces(pageSize, location, isFree) }
                _places.value = places.await()
            } catch (e: Exception) {
                _places.value = emptyList()
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun changeEventColor(){
        _isButtonEventsPressed.value = !(_isButtonEventsPressed.value?: false)
        _isButtonPlacesPressed.value = false
        _isButtonNewsPressed.value = false
    }

    fun changePlacesColor(){
        _isButtonPlacesPressed.value = !(_isButtonPlacesPressed.value?: false)
        _isButtonEventsPressed.value = false
        _isButtonNewsPressed.value = false
    }

    fun changeNewsColor(){
        _isButtonNewsPressed.value = !(_isButtonNewsPressed.value?: false)
        _isButtonPlacesPressed.value = false
        _isButtonEventsPressed.value = false
    }
}