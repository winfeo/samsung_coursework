package com.example.samsung_coursework.domain.use_cases.firebase

import com.example.samsung_coursework.domain.FirebaseRepository

class DeleteFavoritePlaceUseCase(firebaseRepository: FirebaseRepository) {
    private val repository = firebaseRepository

    suspend fun delete(placeId: Int): Int {
        val user = repository.getUserData()
        if (user != null) {
            repository.deleteFavoritePlace(user.userId, placeId)
            //return "Событие добавлено в избранное"
            return 0
        } else {
            //return "Авторизуйтесь, чтобы сохранить событие"
            return 1
        }
    }
}