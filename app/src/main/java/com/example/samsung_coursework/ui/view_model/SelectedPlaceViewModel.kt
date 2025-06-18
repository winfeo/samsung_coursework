package com.example.samsung_coursework.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samsung_coursework.data.FirebaseRepositoryImp
import com.example.samsung_coursework.domain.models.SearchedPlace
import com.example.samsung_coursework.domain.use_cases.firebase.AddFavoritePlaceUseCase
import com.example.samsung_coursework.domain.use_cases.firebase.DeleteFavoritePlaceUseCase
import com.example.samsung_coursework.domain.use_cases.firebase.GetUserDataUseCase
import kotlinx.coroutines.launch

class SelectedPlaceViewModel: ViewModel() {
    private val repository = FirebaseRepositoryImp()
    private val addFavoritePlaceUseCase = AddFavoritePlaceUseCase(repository)
    private val deleteFavoritePlaceUseCase = DeleteFavoritePlaceUseCase(repository)
    private val getUserDataUseCase = GetUserDataUseCase(repository)


    private val _place = MutableLiveData<SearchedPlace>()
    val place: LiveData<SearchedPlace> = _place

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun chosePlace(place: SearchedPlace){
        _place.value = place
    }

    fun updateFavoriteStatus(isFavorite: Boolean) {
        _isFavorite.value = isFavorite
    }

    fun workFavorite(placeId: Int) {
        viewModelScope.launch {
            try {
                val user = getUserDataUseCase.getUser()
                if (user != null) {
                    val currentlyFavorite = _isFavorite.value ?: false
                    if (currentlyFavorite) {
                        deleteFavoritePlaceUseCase.delete(placeId)
                    } else {
                        addFavoritePlaceUseCase.add(placeId)
                    }
                    _isFavorite.value = !currentlyFavorite
                }
            } catch (e: Exception) {
                // TODO: Обработка ошибок
            }
        }
    }
}