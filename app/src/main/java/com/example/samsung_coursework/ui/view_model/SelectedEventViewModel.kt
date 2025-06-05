package com.example.samsung_coursework.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.samsung_coursework.domain.models.Event

class SelectedEventViewModel: ViewModel() {
    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> = _event

    fun choceEvent(event: Event){
        _event.value = event
    }
}