package com.example.samsung_coursework

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.example.samsung_coursework.ui.view_model.FavoriteViewModel
import com.example.samsung_coursework.ui.view_model.HomeViewModel
import com.example.samsung_coursework.ui.view_model.ProfileViewModel
//import com.example.samsung_coursework.ui.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*

class MainActivity : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        applyLanguage()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_menu)
        bottomNav.visibility = View.GONE
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNav.setupWithNavController(navController)

        val cityId = PreferenceManager.getDefaultSharedPreferences(this).getString("primary_city", "msk")?: "msk"
        viewModel.loadEvents(cityId)

        val stayLoggedIn = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("stay_logged_in", true)
        profileViewModel.checkUserId(stayLoggedIn)

        val currentUser = Firebase.auth.currentUser
        if (currentUser != null) {
            favoriteViewModel.updateFavoriteEvents()
            favoriteViewModel.updateFavoritePlaces()
        }

        profileViewModel.isAuthorised.observe(this) { result ->
            if (result) {
                favoriteViewModel.updateFavoriteEvents()
                favoriteViewModel.updateFavoritePlaces()
            }
        }

    }

    private fun applyLanguage() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val lang = sharedPreferences.getString("language", "ru") ?: "ru"

        val locale = Locale(lang)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            createConfigurationContext(config)
        }
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    fun reloadApp() {
        finish()
        startActivity(intent.apply {
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        })
        overridePendingTransition(0, 0)
    }

    override fun onStop() {
        super.onStop()
        val stayLoggedIn = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("stay_logged_in", true)
        if (!stayLoggedIn) {
            profileViewModel.signOut()
        }
    }
}