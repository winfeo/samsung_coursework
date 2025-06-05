package com.example.samsung_coursework.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.samsung_coursework.R
import com.example.samsung_coursework.domain.models.Event
import com.example.samsung_coursework.domain.models.EventDate
import com.example.samsung_coursework.ui.adapters.EventAdapter
import com.example.samsung_coursework.ui.view_model.EventViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*

class FragmentHome : Fragment() {
    private val viewModel: EventViewModel by viewModels()
    private lateinit var recyclerViewAllEvents: RecyclerView
    private lateinit var recyclerViewFreeEvents: RecyclerView
    private lateinit var recyclerViewMostPopularEvents: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var homePage: LinearLayout
    private lateinit var navigationView: BottomNavigationView
    private var adapters = mapOf(
        "allEvents" to EventAdapter(),
        "freeEvents" to EventAdapter(),
        "popularEvents" to EventAdapter()
    )

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewAllEvents = view.findViewById(R.id.recyclerView)
        recyclerViewFreeEvents = view.findViewById(R.id.recyclerView2)
        recyclerViewMostPopularEvents = view.findViewById(R.id.recyclerView3)

        recyclerViewAllEvents.adapter = adapters["allEvents"]
        recyclerViewFreeEvents.adapter = adapters["freeEvents"]
        recyclerViewMostPopularEvents.adapter = adapters["popularEvents"]

        progressBar = view.findViewById(R.id.home_progressBar)
        progressBar.visibility = View.VISIBLE

        homePage = view.findViewById(R.id.home_page)
        homePage.visibility = View.INVISIBLE

        navigationView = requireActivity().findViewById(R.id.bottom_nav_menu)

        viewModel.events.observe(viewLifecycleOwner, Observer { events ->
            adapters["allEvents"]?.submitList(events)
        })

        viewModel.freeEvents.observe(viewLifecycleOwner, Observer { freeEvents ->
            adapters["freeEvents"]?.submitList(freeEvents)
        })

        viewModel.mostPopularEvents.observe(viewLifecycleOwner, Observer { mostPopularEvents ->
            adapters["popularEvents"]?.submitList(mostPopularEvents)
        })

        viewModel.mostPopularEvent.observe(viewLifecycleOwner) { event ->
            updatePopularEventCard(event,view)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.isVisible = isLoading
            if (isLoading) {
                progressBar.fadeIn()
                homePage.fadeOut()
            } else {
                progressBar.fadeOut()
                homePage.fadeIn()
                navigationView.visibility = View.VISIBLE
            }
        }

        viewModel.isPageVisible.observe(viewLifecycleOwner) { isPageVisible ->
            homePage.isVisible = isPageVisible
        }

        //Usecase-ы, загрузка данных
        viewModel.loadEvents()
    }

    private fun updatePopularEventCard(event: Event?, view: View) {
        val image = view.findViewById<ImageView>(R.id.home_eventImage)
        val imageURL = event?.images?.firstOrNull()?.url
        Glide.with(this)
            .load(imageURL)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .into(image)

        val textTitle = view.findViewById<TextView>(R.id.home_eventTitle)
        textTitle.text = event?.title

        val textPlace = view.findViewById<TextView>(R.id.home_eventPlace)
        textPlace.text = "Москва" + ", ${event?.place?.title ?: ""}" + ", ${event?.place?.address ?: ""}"

        val textAge = view.findViewById<TextView>(R.id.home_eventAge)
        val age = when (event?.age_restriction) {
            null, "0", "null" -> "0+"
            else -> "${event.age_restriction}"
        }
        textAge.text = age

        val textDate = view.findViewById<TextView>(R.id.home_eventDate)
        val formatter = SimpleDateFormat("d MMMM", Locale("ru"))

        val eventDates: EventDate? = event?.dates
            ?.filter { it.startTime != null && it.startTime > 0 }
            ?.maxByOrNull { it.startTime!! }

        val startTime = eventDates?.startTime?.let { formatter.format(Date(it * 1000)) }
        val endTime = eventDates?.endTime?.let { formatter.format(Date(it * 1000)) }
        textDate.text = "$startTime - $endTime"

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