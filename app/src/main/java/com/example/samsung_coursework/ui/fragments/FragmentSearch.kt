package com.example.samsung_coursework.ui.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.samsung_coursework.R
import com.example.samsung_coursework.domain.models.Event
import com.example.samsung_coursework.ui.adapters.*
import com.example.samsung_coursework.ui.view_model.SearchViewModel
import com.example.samsung_coursework.ui.view_model.SelectedEventViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class FragmentSearch : Fragment() {
    private val viewModel: SearchViewModel by viewModels()
    private val selectedEventViewModel: SelectedEventViewModel by activityViewModels()
    private lateinit var recyclerViewEvents: RecyclerView
    private val adapterEvent = EventAdapterWide()
    private lateinit var navigationView: BottomNavigationView
    private lateinit var progressBar: ProgressBar
    private var click: EventAdapterWide.ClickInterface? = null



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

        recyclerViewEvents = view.findViewById(R.id.search_recyclerView)
        recyclerViewEvents.adapter = adapterEvent

        viewModel.events.observe(viewLifecycleOwner, Observer { events ->
            adapterEvent?.submitList(events)
        })

        /** TODO имзенить логику обработки, так как при навигации каждый раз вызывается анимация **/
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
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
        }
        adapterEvent?.clickListener = click


        val buttonEvent = view?.findViewById<TextView>(R.id.search_eventsButton)
        viewModel.isButtonEventsPressed.observe(viewLifecycleOwner) { pressed ->
            if (pressed) {
                buttonEvent?.isSelected = true
                buttonEvent?.setTextColor(Color.WHITE)
                val pageSize: Int = 10
                val location: String = "spb"
                val isFree: Boolean = false
                viewModel.searchEvents(pageSize, location, isFree)
            } else {
                buttonEvent?.isSelected = false
                buttonEvent?.setTextColor(Color.BLACK)
            }
        }
        buttonEvent?.setOnClickListener(){
            viewModel.changeEventColor()
        }


        val buttonPlace = view?.findViewById<TextView>(R.id.search_placesButton)
        buttonPlace?.setOnClickListener(){

        }

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