package com.example.samsung_coursework.domain.use_cases

import android.content.Context

class SaveUserIdToPref(context: Context) {
    private val context = context
    fun saveUserIdFromPref(userId: String) {
        val sharedPreferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("USER_ID", userId).apply()
    }
}