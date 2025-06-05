package com.example.samsung_coursework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.example.samsung_coursework.ui.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_menu)
        bottomNav.visibility = View.GONE
        val fragmentContainer = findViewById<FrameLayout>(R.id.fragment_container)

        // Обработка нажатий
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, FragmentHome())
                        .commit()
                    true
                }
                R.id.nav_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, FragmentEvent()) //заменить на FragmentSearch()
                        .commit()
                    true
                }
                R.id.nav_map -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, FragmentMap())
                        .commit()
                    true
                }
                R.id.nav_favorite -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, FragmentFavorite())
                        .commit()
                    true
                }
                R.id.nav_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, FragmentProfile())
                        .commit()
                    true
                }
                else -> false
            }
        }

        // Установите Home как стартовый фрагмент
        bottomNav.selectedItemId = R.id.nav_home
    }
}