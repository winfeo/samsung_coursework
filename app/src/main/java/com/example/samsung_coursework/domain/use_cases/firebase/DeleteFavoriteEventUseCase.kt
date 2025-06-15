package com.example.samsung_coursework.domain.use_cases.firebase

import com.example.samsung_coursework.domain.FirebaseRepository

class DeleteFavoriteEventUseCase(firebaseRepository: FirebaseRepository) {
    private val repository = firebaseRepository
    private val getUserDataUseCase = GetUserDataUseCase(repository)

    suspend fun delete(eventId: Int): Int {
        val user = getUserDataUseCase.getUser()
        if (user != null) {
            repository.deleteFavoriteEvent(user.userId, eventId)
            //return "Событие удалено из избранного"
            return 0
        }
        else{
            //return "Авторизуйтесь, чтобы сохранять события"
            return 1
        }
    }
}