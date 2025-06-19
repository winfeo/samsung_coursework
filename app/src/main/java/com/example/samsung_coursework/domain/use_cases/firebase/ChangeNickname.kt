package com.example.samsung_coursework.domain.use_cases.firebase

import com.example.samsung_coursework.domain.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth

class ChangeNickname(firebaseRepository: FirebaseRepository) {
    private val repository = firebaseRepository
    suspend fun changeNickname(newNickname: String): Boolean {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            repository.changeNickname(userId, newNickname)
            return true
        } else {
            return false
        }
    }

}