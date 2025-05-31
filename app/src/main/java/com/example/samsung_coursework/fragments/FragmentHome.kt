package com.example.samsung_coursework.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.samsung_coursework.R
import com.example.samsung_coursework.adapters.EventAdapter
import com.example.samsung_coursework.models.retrofit.Event
import com.example.samsung_coursework.models.retrofit.EventDate
import com.example.samsung_coursework.view_model.EventViewModel
import java.text.SimpleDateFormat
import java.util.*

class FragmentHome : Fragment() {
    private val viewModel: EventViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EventAdapter


    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        adapter = EventAdapter()
        //recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.events.observe(viewLifecycleOwner, Observer { events ->
            adapter.submitList(events)
        })


        viewModel.mostPopularEvent.observe(viewLifecycleOwner) { event ->
                updatePopularEventCard(event,view)
        }

        // Загрузить все данные
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
}