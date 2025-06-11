package com.example.samsung_coursework.domain.use_cases.firebase

import com.example.samsung_coursework.domain.FirebaseRepository

class SignOutUseCase(firebaseRepository: FirebaseRepository) {
    private val repository = firebaseRepository

    suspend fun signOut(){
        repository.signOut()
    }
}