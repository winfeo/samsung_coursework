package com.example.samsung_coursework.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samsung_coursework.data.FirebaseRepositoryImp
import com.example.samsung_coursework.domain.models.Event
import com.example.samsung_coursework.domain.use_cases.firebase.AddFavoriteEventUseCase
import com.example.samsung_coursework.domain.use_cases.firebase.DeleteFavoriteEventUseCase
import com.example.samsung_coursework.domain.use_cases.firebase.GetUserDataUseCase
import kotlinx.coroutines.launch

class SelectedEventViewModel: ViewModel() {
    /** TODO Hilt **/
    private val repository = FirebaseRepositoryImp()
    private val addFavoriteEventUseCase = AddFavoriteEventUseCase(repository)
    private val deleteFavoriteEventUseCase = DeleteFavoriteEventUseCase(repository)
    private val getUserDataUseCase = GetUserDataUseCase(repository)

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> = _event

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun choseEvent(event: Event){
        _event.value = event
    }

    fun updateFavoriteStatus(isFavorite: Boolean) {
        _isFavorite.value = isFavorite
    }

    fun workFavorite(eventId: Int) {
        viewModelScope.launch {
            try {
                val user = getUserDataUseCase.getUser()
                if (user != null) {
                    val currentlyFavorite = _isFavorite.value ?: false
                    if (currentlyFavorite) {
                        deleteFavoriteEventUseCase.delete(eventId)
                    } else {
                        addFavoriteEventUseCase.add(eventId)
                    }
                    _isFavorite.value = !currentlyFavorite
                }
            } catch (e: Exception) {
                /** TODO если не авторизован **/
                // Обработка ошибок
            }
        }
    }
}