package com.example.samsung_coursework.domain.use_cases.firebase

import com.example.samsung_coursework.domain.FirebaseRepository

class SignUpUseCase(firebaseRepository: FirebaseRepository) {
    private val repository = firebaseRepository

    suspend fun singUp(email: String, password: String): String {
        try {
            val result = repository.signUp(email, password)
            return "Успешная регистрация: ${result.user?.email}"
        } catch (e: Exception) {
            return "Ошибка регистрации: ${e.message}"
        }
    }
}