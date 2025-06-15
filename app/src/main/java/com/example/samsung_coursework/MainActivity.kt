package com.example.samsung_coursework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.samsung_coursework.ui.view_model.FavoriteViewModel
import com.example.samsung_coursework.ui.view_model.HomeViewModel
//import com.example.samsung_coursework.ui.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_menu)
        bottomNav.visibility = View.GONE
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNav.setupWithNavController(navController)
        /**TODO сделать экран, на котор при первом запуске прилож, будет ф-ия выбора города **/
        /**TODO и языка? **/
        viewModel.loadEvents()//по умолчанию msk в usecase-ах и запросах API пока будет

        favoriteViewModel.updateFavoriteEvents()

    }
}