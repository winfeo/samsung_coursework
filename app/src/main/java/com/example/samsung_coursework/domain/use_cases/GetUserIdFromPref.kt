package com.example.samsung_coursework.domain.use_cases

import android.content.Context

class GetUserIdFromPref(context: Context) {
    private val context = context
    fun getUserIdFromPref(): String? {
        val sharedPreferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE)
        return sharedPreferences.getString("USER_ID", null)
    }
}