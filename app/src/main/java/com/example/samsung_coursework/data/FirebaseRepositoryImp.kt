package com.example.samsung_coursework.data

import com.example.samsung_coursework.domain.FirebaseRepository
import com.example.samsung_coursework.domain.models.User
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseRepositoryImp : FirebaseRepository {
    private val auth = Firebase.auth
    private val database = Firebase.database("https://eventfinder-46a84-default-rtdb.europe-west1.firebasedatabase.app")
    private val reference = database.getReference("users")

    override suspend fun signUp(email: String, password: String): AuthResult {
        return auth.createUserWithEmailAndPassword(email,password).await()
    }

    override suspend fun signIn(email: String, password: String): AuthResult {
        return auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun createNewUser(user: User) {
        reference.child(user.userId).setValue(user).await()
    }
}