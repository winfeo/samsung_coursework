package com.example.samsung_coursework.domain.use_cases.firebase

import com.example.samsung_coursework.domain.FirebaseRepository
import com.example.samsung_coursework.domain.models.User

class GetUserDataUseCase(firebaseRepository: FirebaseRepository) {
    private val repository = firebaseRepository
    suspend fun getUser(): User? {
        val user = repository.getUserData()
        return user
    }
}