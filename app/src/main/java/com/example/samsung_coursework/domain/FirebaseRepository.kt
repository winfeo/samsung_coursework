package com.example.samsung_coursework.domain

import com.example.samsung_coursework.domain.models.User
import com.google.firebase.auth.AuthResult

interface FirebaseRepository {
    suspend fun signUp(email: String, password: String): AuthResult
    //suspend fun signIn(email: String, password: String): String
    //suspend fun signOut()
    //suspend fun getUser(): User?
}