package com.example.samsung_coursework.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.samsung_coursework.ui.fragments.FragmentFavoriteEvents
import com.example.samsung_coursework.ui.fragments.FragmentFavoritePlaces

class FavoritePagerAdapter(fragment: FragmentActivity): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FragmentFavoriteEvents()
            1 -> FragmentFavoritePlaces()
            else -> throw IllegalArgumentException("-вайб")
        }
    }
}