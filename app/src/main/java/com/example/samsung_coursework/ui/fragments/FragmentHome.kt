package com.example.samsung_coursework.ui.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.samsung_coursework.R
import com.example.samsung_coursework.domain.models.Event
import com.example.samsung_coursework.domain.models.EventDate
import com.example.samsung_coursework.ui.adapters.EventAdapter
import com.example.samsung_coursework.ui.view_model.FavoriteViewModel
import com.example.samsung_coursework.ui.view_model.HomeViewModel
import com.example.samsung_coursework.ui.view_model.SelectedEventViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*

class FragmentHome : Fragment() {
    private val viewModel: HomeViewModel by activityViewModels()
    private val selectedEventViewModel: SelectedEventViewModel by activityViewModels()
    private val favoriteViewModel: FavoriteViewModel by activityViewModels()
    private lateinit var recyclerViewAllEvents: RecyclerView
    private lateinit var recyclerViewFreeEvents: RecyclerView
    private lateinit var recyclerViewMostPopularEvents: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var homePage: LinearLayout
    private lateinit var navigationView: BottomNavigationView
    private var click: EventAdapter.ClickInterface? = null
    private var lastPositionAdapter = -1
    private var firstLoad = true
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

        val window = requireActivity().window
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        WindowInsetsControllerCompat(window, requireView()).isAppearanceLightStatusBars = true

        val prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireContext())
        val savedCityCode = prefs.getString("primary_city", "msk") ?: "msk"

        val cityNames = resources.getStringArray(R.array.home_cityList)
        val cityCodes = resources.getStringArray(R.array.home_cityListAPI)
        val mapCity = cityNames.zip(cityCodes).toMap()
        val reverseMapCity = cityCodes.zip(cityNames).toMap()

        val spinner = view.findViewById<Spinner>(R.id.home_spinner)
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.home_cityList,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val initialPosition = cityCodes.indexOf(savedCityCode).takeIf { it >= 0 } ?: 0
        spinner.setSelection(initialPosition)
        lastPositionAdapter = initialPosition

        viewModel.loadEvents(savedCityCode)
        firstLoad = true

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (firstLoad) {
                    firstLoad = false
                    lastPositionAdapter = position
                    return
                }
                if (position != lastPositionAdapter) {
                    lastPositionAdapter = position
                    val selectedCityCode = cityCodes[position]
                    viewModel.loadEvents(selectedCityCode)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }






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

        favoriteViewModel.favoriteEventIds.observe(viewLifecycleOwner) { ids ->
            adapters.values.forEach { adapter ->
                adapter.favoriteEventIds = ids
            }
        }


        click = object : EventAdapter.ClickInterface {
            override fun onClick(event: Event) {
                selectedEventViewModel.choseEvent(event)
                findNavController().navigate(R.id.action_fragmentHome_to_fragmentEvent)
            }

            override fun onFavoriteClick(event: Event, isCurrentlyFavorite: Boolean) {
                if (isCurrentlyFavorite) {
                    favoriteViewModel.deleteFavoriteEvent(event.id)
                } else {
                    favoriteViewModel.addFavoriteEvent(event.id)
                }
            }
        }

        adapters["allEvents"]?.clickListener = click
        adapters["freeEvents"]?.clickListener = click
        adapters["popularEvents"]?.clickListener = click
        val popularCard = view?.findViewById<CardView>(R.id.home_popularEvent)
        popularCard?.setOnClickListener {
            viewModel.mostPopularEvent.value?.let { event ->
                click?.onClick(event)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.favoriteEventIds.value?.let { ids ->
            adapters.values.forEach { adapter ->
                adapter.favoriteEventIds = ids
            }
        }
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

        val favorite = view.findViewById<ImageButton>(R.id.home_addToFavoriteButton)
        val isFavorite = favoriteViewModel.isFavorite(event?.id ?: 0)
        favoriteViewModel.favoriteEventIds.observe(viewLifecycleOwner) { ids ->
            event?.let {
                val isFavorite = ids.contains(it.id)
                favorite.setImageResource(
                    if (isFavorite) R.drawable.ic_favorite_full
                    else R.drawable.ic_favorite
                )
            }
        }

        favorite.setOnClickListener {
            event?.let { currentEvent ->
                val isCurrentlyFavorite = favoriteViewModel.isFavorite(currentEvent.id)
                if (isCurrentlyFavorite) {
                    favoriteViewModel.deleteFavoriteEvent(currentEvent.id)
                } else {
                    favoriteViewModel.addFavoriteEvent(currentEvent.id)
                }
            }
        }

        val textPlace = view.findViewById<TextView>(R.id.home_eventPlace)
        textPlace.text = "${event?.place?.title ?: ""}" + ", ${event?.place?.address ?: ""}"

        val textAge = view.findViewById<TextView>(R.id.home_eventAge)
        val age = when (event?.age_restriction) {
            null, "0", "null" -> "0+"
            else -> "${event.age_restriction}"
        }
        textAge.text = age

        val textDate = view.findViewById<TextView>(R.id.home_eventDate)
        // Дата
        val formatter = SimpleDateFormat("d MMMM", Locale("ru"))

        val eventDates: EventDate? = event?.dates
            ?.filter { it.endTimeNumber != null && it.endTimeNumber > System.currentTimeMillis() / 1000 }
            ?.minByOrNull { it.endTimeNumber!! }

        val startTime = eventDates?.startTimeNumber?.let { formatter.format(Date(it * 1000)) }
        val endTime = eventDates?.endTimeNumber?.let { formatter.format(Date(it * 1000)) }
        if(!startTime.equals(endTime)){
            val startTime = eventDates?.startTimeNumber?.let { formatter.format(Date(it * 1000)) }
            val endTime = eventDates?.endTimeNumber?.let { formatter.format(Date(it * 1000)) }
            textDate.text = "$startTime - $endTime"
        }
        else textDate.text = "$endTime"

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