package com.example.samsung_coursework.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.samsung_coursework.R
import com.example.samsung_coursework.domain.models.Event
import com.example.samsung_coursework.ui.adapters.EventAdapterWide
import com.example.samsung_coursework.ui.adapters.FavoritePagerAdapter
import com.example.samsung_coursework.ui.view_model.FavoriteViewModel
import com.example.samsung_coursework.ui.view_model.SelectedEventViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class FragmentFavorite : Fragment() {
    private val viewModel: FavoriteViewModel by activityViewModels()
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var navigationView: BottomNavigationView

    /*
    private val selectedEventViewModel: SelectedEventViewModel by activityViewModels()
    private lateinit var recyclerViewFavoriteEvents: RecyclerView
    private val adapterEvent = EventAdapterWide()
    private var click: EventAdapterWide.ClickInterface? = null
    private lateinit var favoriteEventCounter: TextView
     */

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationView = requireActivity().findViewById(R.id.bottom_nav_menu)
        navigationView.visibility = View.VISIBLE

        viewPager = view.findViewById(R.id.favorite_viewPager)
        tabLayout = view.findViewById(R.id.favorite_tabLayout)
        viewPager.adapter = FavoritePagerAdapter(requireActivity())

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                /** TODO сделать строковый массив-ресурс **/
                0 -> "События"
                1 -> "Места"
                else -> ""
            }
        }.attach()

        /*
        viewModel.updateFavoriteEvents()
         */




        /*
        favoriteEventCounter = view.findViewById<TextView>(R.id.favorite_counter)

        viewModel.updateFavoriteEvents()

        recyclerViewFavoriteEvents = view.findViewById(R.id.favorite_RecyclerView)
        recyclerViewFavoriteEvents.adapter = adapterEvent

        viewModel.favoriteEvents.observe(viewLifecycleOwner) { events ->
            val favoriteList = events.toMutableList()
            Log.d("FavoriteFragment", "Favorite events count: ${events.size}")
            adapterEvent.submitList(favoriteList) {
                recyclerViewFavoriteEvents.scrollToPosition(0)
            }
        }

        viewModel.favoriteEventIds.observe(viewLifecycleOwner) { ids ->
            adapterEvent.favoriteEventIds = ids
        }

        viewModel.favoriteCount.observe(viewLifecycleOwner) { count ->
            favoriteEventCounter.text = count.toString()
            favoriteEventCounter.visibility = if (count > 0) View.VISIBLE else View.GONE
        }





            click = object : EventAdapterWide.ClickInterface {
            override fun onClick(event: Event) {
                selectedEventViewModel.choseEvent(event)
                findNavController().navigate(R.id.action_fragmentFavorite_to_fragmentEvent)
            }

            override fun onFavoriteClick(event: Event, isCurrentlyFavorite: Boolean) {
                if (isCurrentlyFavorite) {
                    viewModel.deleteFavoriteEvent(event.id)
                }
            }
        }
        adapterEvent.clickListener = click

         */


    }
    /*
    override fun onResume() {
        super.onResume()
        viewModel.updateFavoriteEvents()
    }

     */

}