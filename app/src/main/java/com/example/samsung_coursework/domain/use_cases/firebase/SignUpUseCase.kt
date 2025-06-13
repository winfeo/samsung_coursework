package com.example.samsung_coursework.domain.use_cases.firebase

import android.provider.ContactsContract.CommonDataKinds.Nickname
import com.example.samsung_coursework.domain.FirebaseRepository
import com.example.samsung_coursework.domain.models.User

class SignUpUseCase(firebaseRepository: FirebaseRepository) {
    private val repository = firebaseRepository

    suspend fun signUp(nickname: String, email: String, password: String): String {
        try {
            val result = repository.signUp(email, password)
            val userId = result.user?.uid?: throw Exception("Не удалось найти ID")
            val user = User(
                userId = userId,
                userNickname = nickname,
                userEmail = email
            )
            repository.createNewUser(user)
            return "Успешная регистрация: ${result.user?.email}"
        } catch (e: Exception) {
            return "Ошибка регистрации: ${e.message}"
        }
    }
}