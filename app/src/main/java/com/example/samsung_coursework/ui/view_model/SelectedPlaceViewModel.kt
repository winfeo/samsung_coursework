package com.example.samsung_coursework.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.samsung_coursework.domain.models.SearchedPlace

class SelectedPlaceViewModel: ViewModel() {
    private val _place = MutableLiveData<SearchedPlace>()
    val place: LiveData<SearchedPlace> = _place

    fun chosePlace(place: SearchedPlace){
        _place.value = place
    }
}