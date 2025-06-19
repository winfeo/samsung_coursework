package com.example.samsung_coursework.data

import com.example.samsung_coursework.domain.FirebaseRepository
import com.example.samsung_coursework.domain.models.User
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
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






    override suspend fun getUserData(): User? {
        val userId = auth.currentUser?.uid
        val snapshot = userId?.let { reference.child(it).get().await() }
        val user = snapshot?.getValue(User::class.java)
        return user
    }

    //избран событ
    override suspend fun addFavoriteEvent(userId: String, eventId: Int) {
        val currentList = getFavoriteEventsIds(userId)

        if (!currentList.contains(eventId)) {
            val updatedList = currentList.toMutableList()
            updatedList.add(eventId)
            reference.child(userId).child("userFavoriteEvents").setValue(updatedList).await()
        }
    }

    override suspend fun deleteFavoriteEvent(userId: String, eventId: Int) {
        val currentList = getFavoriteEventsIds(userId)

        if (currentList.contains(eventId)) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(eventId)
            reference.child(userId).child("userFavoriteEvents").setValue(updatedList).await()
        }
    }

    override suspend fun updateFavoriteEvents(userId: String, events: List<Int>) {
        reference.child(userId).child("userFavoriteEvents").setValue(events).await()
    }


    override suspend fun getFavoriteEventsIds(userId: String): List<Int> {
        val snapshot = reference.child(userId).child("userFavoriteEvents").get().await()
        return snapshot.getValue<List<Int>>() ?: emptyList()
    }






    //избран места
    override suspend fun updateFavoritePlaces(userId: String, places: List<Int>) {
        reference.child(userId).child("userFavoritePlaces").setValue(places).await()
    }

    override suspend fun addFavoritePlace(userId: String, eventId: Int) {
        val currentList = getFavoritePlaceIds(userId)

        if (!currentList.contains(eventId)) {
            val updatedList = currentList.toMutableList()
            updatedList.add(eventId)
            reference.child(userId).child("userFavoritePlaces").setValue(updatedList).await()
        }
    }

    override suspend fun deleteFavoritePlace(userId: String, eventId: Int) {
        val currentList = getFavoritePlaceIds(userId)

        if (currentList.contains(eventId)) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(eventId)
            reference.child(userId).child("userFavoritePlaces").setValue(updatedList).await()
        }
    }

    override suspend fun getFavoritePlaceIds(userId: String): List<Int> {
        val snapshot = reference.child(userId).child("userFavoritePlaces").get().await()
        return snapshot.getValue<List<Int>>() ?: emptyList()
    }


    override suspend fun changeNickname(userId: String, newNickname: String) {
        reference.child(userId).child("userNickname").setValue(newNickname).await()
    }


}