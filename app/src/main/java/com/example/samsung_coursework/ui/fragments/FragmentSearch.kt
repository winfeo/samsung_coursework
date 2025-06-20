package com.example.samsung_coursework.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.samsung_coursework.R
import com.example.samsung_coursework.domain.models.*
import com.example.samsung_coursework.ui.adapters.*
import com.example.samsung_coursework.ui.view_model.*
import com.google.android.material.bottomnavigation.BottomNavigationView


class FragmentSearch : Fragment() {
    private val viewModel: SearchViewModel by activityViewModels()
    private val selectedEventViewModel: SelectedEventViewModel by activityViewModels()
    private val selectedPlaceViewModel: SelectedPlaceViewModel by activityViewModels()
    private val favoriteViewModel: FavoriteViewModel by activityViewModels()
    private lateinit var recyclerViewEvents: RecyclerView
    private val adapterEvent = EventAdapterWide()
    private val adapterPlace = SearchedPlaceAdapter()
    private lateinit var navigationView: BottomNavigationView
    private lateinit var progressBar: ProgressBar
    private var click: EventAdapterWide.ClickInterface? = null
    private var click2: SearchedPlaceAdapter.ClickInterface? = null
    private lateinit var searchBarText: EditText
    private lateinit var searchButtonSearch: ImageButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val window = requireActivity().window
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        WindowInsetsControllerCompat(window, requireView()).isAppearanceLightStatusBars = true

        navigationView = requireActivity().findViewById(R.id.bottom_nav_menu)
        navigationView.visibility = View.VISIBLE
        progressBar = view.findViewById(R.id.search_progressBar)

        favoriteViewModel.updateFavoriteEvents()

        recyclerViewEvents = view.findViewById(R.id.search_recyclerView)

        // Обзёрверы
        viewModel.events.observe(viewLifecycleOwner) { events ->
            adapterEvent.submitList(events)
        }

        viewModel.places.observe(viewLifecycleOwner) { places ->
            adapterPlace.submitList(places)
        }

        favoriteViewModel.favoriteEventIds.observe(viewLifecycleOwner) { ids ->
            adapterEvent.favoriteEventIds = ids
        }

        favoriteViewModel.favoritePlaceIds.observe(viewLifecycleOwner) { ids ->
            adapterPlace.favoritePlaceIds = ids
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.isVisible = isLoading
            if (isLoading) {
                progressBar.fadeIn()
                recyclerViewEvents.fadeOut()
            } else {
                progressBar.fadeOut()
                recyclerViewEvents.fadeIn()
            }
        }

        click = object : EventAdapterWide.ClickInterface {
            override fun onClick(event: Event) {
                selectedEventViewModel.choseEvent(event)
                findNavController().navigate(R.id.action_fragmentSearch_to_fragmentEvent)
            }
            override fun onFavoriteClick(event: Event, isCurrentlyFavorite: Boolean) {
                if (isCurrentlyFavorite) {
                    favoriteViewModel.deleteFavoriteEvent(event.id)
                } else {
                    favoriteViewModel.addFavoriteEvent(event.id)
                }
            }
        }
        adapterEvent.clickListener = click

        click2 = object : SearchedPlaceAdapter.ClickInterface {
            override fun onClick(place: SearchedPlace) {
                selectedPlaceViewModel.chosePlace(place)
                findNavController().navigate(R.id.action_fragmentSearch_to_fragmentPlace)
            }
            override fun onFavoriteClick(place: SearchedPlace, isCurrentlyFavorite: Boolean) {
                if (isCurrentlyFavorite) {
                    favoriteViewModel.deleteFavoritePlace(place.id)
                } else {
                    favoriteViewModel.addFavoritePlace(place.id)
                }
            }
        }
        adapterPlace.clickListener = click2

        val filterButton = view.findViewById<ImageButton>(R.id.search_buttonFilters)
        filterButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentSearch_to_fragmentFilters)
        }

        val buttonEvent = view.findViewById<TextView>(R.id.search_eventsButton)
        val buttonPlace = view.findViewById<TextView>(R.id.search_placesButton)

        viewModel.isButtonEventsPressed.observe(viewLifecycleOwner) { pressed ->
            buttonEvent.isSelected = pressed
            buttonEvent.setTextColor(if (pressed) Color.WHITE else Color.BLACK)
            if (pressed) {
                recyclerViewEvents.adapter = adapterEvent
            }
        }

        searchBarText = view.findViewById(R.id.search_barText)
        searchButtonSearch = view.findViewById(R.id.search_buttonSearch)

        searchButtonSearch.setOnClickListener {
            val query = searchBarText.text.toString().trim()
            if (query.isNotEmpty()) {
                viewModel.search(query)
            }
        }

        viewModel.isButtonPlacesPressed.observe(viewLifecycleOwner) { pressed ->
            buttonPlace.isSelected = pressed
            buttonPlace.setTextColor(if (pressed) Color.WHITE else Color.BLACK)
            if (pressed) {
                recyclerViewEvents.adapter = adapterPlace
            }
        }

        buttonEvent.setOnClickListener {
            viewModel.changeEventColor()
        }

        buttonPlace.setOnClickListener {
            viewModel.changePlacesColor()
        }

        viewModel.searchWithCurrentFilters()
    }

    fun View.fadeIn(duration: Long = 300) {
        this.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .setDuration(duration)
                .withEndAction { alpha = 1f }
        }
    }

    fun View.fadeOut(duration: Long = 300) {
        this.animate()
            .alpha(0f)
            .setDuration(duration)
            .withEndAction {
                visibility = View.GONE
                alpha = 1f
            }
    }
}
