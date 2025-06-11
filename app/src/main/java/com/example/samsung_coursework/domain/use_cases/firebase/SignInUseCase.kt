package com.example.samsung_coursework.domain.use_cases.firebase

import com.example.samsung_coursework.domain.FirebaseRepository

class SignInUseCase(firebaseRepository: FirebaseRepository) {
    private val repository = firebaseRepository

    suspend fun signIn(email: String, password: String): Pair<Boolean, String>{
        try {
            val result = repository.signIn(email, password)
            return (true to "Успешная авторизация: ${result.user?.email}")
        }
        catch (e: Exception){
            return (false to "Ошибка авторизации: ${e.message}")
        }
    }
}