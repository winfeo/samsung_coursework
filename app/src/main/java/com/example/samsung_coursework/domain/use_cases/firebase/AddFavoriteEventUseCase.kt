package com.example.samsung_coursework.domain.use_cases.firebase

import com.example.samsung_coursework.domain.FirebaseRepository

class AddFavoriteEventUseCase(firebaseRepository: FirebaseRepository) {
    private val repository = firebaseRepository
    private val getUserDataUseCase = GetUserDataUseCase(repository)

    suspend fun add(eventId: Int): Int {
        val user = getUserDataUseCase.getUser()
        if (user != null) {
            repository.addFavoriteEvent(user.userId, eventId)
            //return "Событие добавлено в избранное"
            return 0
        }
        else{
            //return "Авторизуйтесь, чтобы сохранить событие"
            return 1
        }
    }
}
