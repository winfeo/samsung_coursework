package com.example.samsung_coursework.domain.use_cases.firebase

import android.provider.ContactsContract.CommonDataKinds.Nickname
import com.example.samsung_coursework.R
import com.example.samsung_coursework.domain.FirebaseRepository
import com.example.samsung_coursework.domain.models.User

class SignUpUseCase(firebaseRepository: FirebaseRepository) {
    private val repository = firebaseRepository

    suspend fun signUp(nickname: String, email: String, password: String): Pair<Boolean, Int> {
        try {
            val result = repository.signUp(email, password)
            val userId = result.user?.uid?: throw Exception("Не удалось найти ID")
            val user = User(
                userId = userId,
                userNickname = nickname,
                userEmail = email
            )
            repository.createNewUser(user)
            return (true to R.string.profile_toastRegGood)
        } catch (e: Exception) {
            return (true to R.string.profile_toastRegBad)
        }
    }
}