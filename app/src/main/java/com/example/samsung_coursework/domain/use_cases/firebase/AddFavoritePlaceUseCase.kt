package com.example.samsung_coursework.domain.use_cases.firebase

import com.example.samsung_coursework.domain.FirebaseRepository

class AddFavoritePlaceUseCase(firebaseRepository: FirebaseRepository) {
    private val repository = firebaseRepository

    suspend fun add(placeId: Int): Int {
        val user = repository.getUserData()
        if(user != null){
            repository.addFavoritePlace(user.userId, placeId)
            //return "Событие добавлено в избранное"
            return 0
        }
        else{
            //return "Авторизуйтесь, чтобы сохранить событие"
            return 1
        }
    }
}