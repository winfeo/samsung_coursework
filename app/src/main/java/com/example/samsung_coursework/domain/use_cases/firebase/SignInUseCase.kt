package com.example.samsung_coursework.domain.use_cases.firebase

import com.example.samsung_coursework.R
import com.example.samsung_coursework.domain.FirebaseRepository

class SignInUseCase(firebaseRepository: FirebaseRepository) {
    private val repository = firebaseRepository

    suspend fun signIn(email: String, password: String): Pair<Boolean, Int>{
        try {
            val result = repository.signIn(email, password)
            return (true to R.string.profile_toastAuthGood)
        }
        catch (e: Exception){
            return (false to R.string.profile_toastAuthBad)
        }
    }
}