package com.example.samsung_coursework.ui.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samsung_coursework.data.ApiRepositoryImp
import com.example.samsung_coursework.domain.models.*
import com.example.samsung_coursework.domain.use_cases.GetEventsByText
import com.example.samsung_coursework.domain.use_cases.GetPlacesByText
import com.example.samsung_coursework.domain.use_cases.SearchEventsUseCase
import com.example.samsung_coursework.domain.use_cases.SearchPlacesUseCase
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val repository = ApiRepositoryImp()
    private val searchEventsUseCase = SearchEventsUseCase(repository)
    private val searchPlacesUseCase = SearchPlacesUseCase(repository)
    private val getEventsByText = GetEventsByText(repository)
    private val getPlacesByText = GetPlacesByText(repository)

    private val _isButtonEventsPressed = MutableLiveData(true) // События выбраны по умолчанию
    private val _isButtonPlacesPressed = MutableLiveData(false)
    private val _isLoading = MutableLiveData(false)
    private val _events = MutableLiveData<List<Event>>()
    private val _places = MutableLiveData<List<SearchedPlace>>()
    private val _currentFilters = MutableLiveData<FilterOptions>(
        FilterOptions(searchType = "event", location = "msk", isFree = false,
            orderBy = "-favorites_count", pageSize = 10)
    )

    val isButtonEventsPressed: LiveData<Boolean> = _isButtonEventsPressed
    val isButtonPlacesPressed: LiveData<Boolean> = _isButtonPlacesPressed
    val isLoading: LiveData<Boolean> = _isLoading
    val events: LiveData<List<Event>> = _events
    val places: LiveData<List<SearchedPlace>> = _places
    val currentFilters: LiveData<FilterOptions> = _currentFilters

    fun searchEvents(
        pageSize: Int = 10,
        location: String = "msk",
        isFree: Boolean = false,
        orderBy: String? = null
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _events.value = searchEventsUseCase.searchEvents(
                    pageSize = pageSize,
                    location = location,
                    isFree = isFree,
                    orderBy = orderBy
                )
            } catch (e: Exception) {
                _events.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchPlaces(
        pageSize: Int = 10,
        location: String = "msk",
        isFree: Boolean = false,
        orderBy: String? = null
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _places.value = searchPlacesUseCase.searchPlaces(
                    pageSize = pageSize,
                    location = location,
                    isFree = isFree,
                    orderBy = orderBy
                )
            } catch (e: Exception) {
                _places.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchWithCurrentFilters() {
        val filters = _currentFilters.value ?: return

        when (filters.searchType) {
            "event" -> searchEvents(
                pageSize = filters.pageSize?: 5,
                location = filters.location?: "msk",
                isFree = filters.isFree?: false,
                orderBy = filters.orderBy?: "-favorites_count"
            )
            "place" -> searchPlaces(
                pageSize = filters.pageSize?: 5,
                location = filters.location?: "msk",
                isFree = filters.isFree?: false,
                orderBy = filters.orderBy?: "-favorites_count"
            )
        }
    }

    fun changeEventColor() {
        _currentFilters.value = _currentFilters.value?.copy(searchType = "event")
        _isButtonEventsPressed.value = true
        _isButtonPlacesPressed.value = false
        searchWithCurrentFilters()
    }

    fun changePlacesColor() {
        _currentFilters.value = _currentFilters.value?.copy(searchType = "place")
        _isButtonEventsPressed.value = false
        _isButtonPlacesPressed.value = true
        searchWithCurrentFilters()
    }

    fun applyFilters(filters: FilterOptions) {
        _currentFilters.value = filters
        searchWithCurrentFilters()
    }

    fun search(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                if (_currentFilters.value?.searchType == "event") {
                    val eventsResult = getEventsByText.searchEventsByText(query)
                    _events.value = eventsResult
                } else {
                    val placesResult = getPlacesByText.searchPlacesByText(query = query)
                    _places.value = placesResult
                }
            } catch (e: Exception) {
                _events.value = emptyList()
                _places.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}