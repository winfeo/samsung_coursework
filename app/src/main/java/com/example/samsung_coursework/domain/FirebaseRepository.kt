package com.example.samsung_coursework.domain

import com.example.samsung_coursework.domain.models.User
import com.google.firebase.auth.AuthResult

interface FirebaseRepository {
    suspend fun signUp(email: String, password: String): AuthResult
    suspend fun signIn(email: String, password: String): AuthResult
    suspend fun signOut()

    /** TODO еализовать usecase-ы для работы с избранным **/
    suspend fun createNewUser(user: User)
    //suspend fun getUser(): User?
}