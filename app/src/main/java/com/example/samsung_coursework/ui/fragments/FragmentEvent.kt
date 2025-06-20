package com.example.samsung_coursework.ui.fragments

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.samsung_coursework.R
import com.example.samsung_coursework.data.retrofit.CategoryTranslatorEvent
import com.example.samsung_coursework.domain.models.Event
import com.example.samsung_coursework.domain.models.EventDate
import com.example.samsung_coursework.ui.view_model.FavoriteViewModel
import com.example.samsung_coursework.ui.view_model.SelectedEventViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*

class FragmentEvent : Fragment() {
    private lateinit var toolbar: Toolbar
    private val selectedEventViewModel: SelectedEventViewModel by activityViewModels()
    private val favoriteViewModel: FavoriteViewModel by activityViewModels()
    private lateinit var favoriteButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_menu)
        bottomNav.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.event_toolbar)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener{
            findNavController().navigateUp()
        }
        //toolbar.title = "Событие дня"

        val window = requireActivity().window
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        WindowInsetsControllerCompat(window, view).isAppearanceLightStatusBars = false

        favoriteButton = view.findViewById<ImageButton>(R.id.event_favorite_button)

        selectedEventViewModel.event.observe(viewLifecycleOwner) { event ->
            updateCardInfo(event, view)
            selectedEventViewModel.updateFavoriteStatus(favoriteViewModel.isFavorite(event.id))
        }

        selectedEventViewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            favoriteButton.setImageResource(
                if (isFavorite) R.drawable.ic_favorite_full
                else R.drawable.ic_favorite
            )
        }

        /*
        selectedEventViewModel.event.observe(viewLifecycleOwner){ event ->
            updateCardInfo(event, view)
        }

         */


        val descriptionContainer = view.findViewById<LinearLayout>(R.id.description_container)
        val descriptionContent = view.findViewById<LinearLayout>(R.id.description_content)
        val descriptionText = view.findViewById<TextView>(R.id.description_text)
        val descriptionArrow = view.findViewById<ImageView>(R.id.description_arrow)
        var isExpanded = false

        descriptionContainer.setOnClickListener {
            isExpanded = !isExpanded

            val rotationAnim = ValueAnimator.ofFloat(
                descriptionArrow.rotation,
                if (isExpanded) 180f else 0f
            ).apply {
                duration = 300
                interpolator = AccelerateDecelerateInterpolator()
                addUpdateListener { animator ->
                    descriptionArrow.rotation = animator.animatedValue as Float
                }
            }

            if (isExpanded) {
                descriptionText.maxLines = Integer.MAX_VALUE
                descriptionText.ellipsize = null

                descriptionText.measure(
                    View.MeasureSpec.makeMeasureSpec(descriptionText.width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                )
                val fullHeight = descriptionText.measuredHeight

                ValueAnimator.ofInt(descriptionContent.height, fullHeight).apply {
                    duration = 300
                    interpolator = AccelerateDecelerateInterpolator()
                    addUpdateListener { animator ->
                        val value = animator.animatedValue as Int
                        descriptionContent.layoutParams.height = value
                        descriptionContent.requestLayout()
                    }
                    start()
                }
            } else {
                descriptionText.maxLines = 3
                descriptionText.ellipsize = TextUtils.TruncateAt.END

                val initialHeight = descriptionText.lineHeight * 3
                ValueAnimator.ofInt(descriptionContent.height, initialHeight).apply {
                    duration = 300
                    interpolator = AccelerateDecelerateInterpolator()
                    addUpdateListener { animator ->
                        val value = animator.animatedValue as Int
                        descriptionContent.layoutParams.height = value
                        descriptionContent.requestLayout()
                    }
                    start()
                }
            }

            rotationAnim.start()
        }

        favoriteButton.setOnClickListener {
            selectedEventViewModel.event.value?.id?.let { eventId ->
                val currentlyFavorite = selectedEventViewModel.isFavorite.value ?: false
                if (currentlyFavorite) {
                    favoriteViewModel.deleteFavoriteEvent(eventId)
                } else {
                    favoriteViewModel.addFavoriteEvent(eventId)
                }
                selectedEventViewModel.workFavorite(eventId)
            }
        }

    }

    private fun updateCardInfo(event: Event, view: View){
        val imageView = view.findViewById<ImageView>(R.id.event_image)
        val categoriesText = view.findViewById<TextView>(R.id.event_categories)
        val ageText = view.findViewById<TextView>(R.id.event_age)
        val titleText = view.findViewById<TextView>(R.id.event_title)
        val descriptionText = view.findViewById<TextView>(R.id.description_text)
        val timeDateText = view.findViewById<TextView>(R.id.event_timeDate)
        val timeTimeText = view.findViewById<TextView>(R.id.event_timeTime)
        val locationCityText = view.findViewById<TextView>(R.id.event_locationCity)
        val locationDetailedText = view.findViewById<TextView>(R.id.event_locationDetailed)
        val priceText = view.findViewById<TextView>(R.id.event_price)
        val scheduleText = view.findViewById<TextView>(R.id.event_schedule)

        val imageURL = event?.images?.firstOrNull()?.url
        Glide.with(view)
            .load(imageURL)
            .error(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .into(imageView)

        val translatedCategories = CategoryTranslatorEvent.translateCategory(event.categories)
        categoriesText.text = translatedCategories

        val textAge = when (event.age_restriction) {
            null, "0", "null" -> "0+"
            else -> "${event.age_restriction}"
        }
        ageText.text = textAge

        titleText.text = event?.title
        descriptionText.text = event?.body_text //description?

        /*
        val isFavorite = favoriteViewModel.isFavorite(event.id)
        favoriteButton.setImageResource(
            if (isFavorite) R.drawable.ic_favorite_full
            else R.drawable.ic_favorite
        )

         */

        //Дни
        val formatter = SimpleDateFormat("d MMMM", Locale("ru"))
        val eventDates: EventDate? = event?.dates
            ?.filter { it.endTimeNumber != null && it.endTimeNumber > System.currentTimeMillis() / 1000 }
            ?.minByOrNull { it.endTimeNumber!! }
        //?.maxByOrNull { it.endTime!! }
        val startTime = eventDates?.startTimeNumber?.let { formatter.format(Date(it * 1000)) }
        val endTime = eventDates?.endTimeNumber?.let { formatter.format(Date(it * 1000)) }
        if(startTime.isNullOrBlank() && endTime.isNullOrBlank()){
            val timeContainer = view.findViewById<LinearLayout>(R.id.event_timeContainer)
            timeContainer.visibility = View.GONE
        }
        else{
            if(!startTime.equals(endTime)){
                val add = view.context.getString(R.string.home_endWithEvent) //?
                val endTime = eventDates?.endTimeNumber?.let { formatter.format(Date(it * 1000 + 24 * 60 * 60 * 1000)) }
                timeDateText.text = "$add $endTime"
            }
            else timeDateText.text = "$endTime"
        }


        //Время
        val startHour1 = eventDates?.startTime
        val endHour1 = eventDates?.endTime
        var startHour = ""
        var endHour = ""
        if(startHour1 != null){
            startHour = SimpleDateFormat("HH:mm", Locale("ru")).format(
                SimpleDateFormat("HH:mm:ss", Locale("ru")).parse(startHour1)
            )
        }
        if(endHour1 != null){
            endHour = SimpleDateFormat("HH:mm", Locale("ru")).format(
                SimpleDateFormat("HH:mm:ss", Locale("ru")).parse(endHour1)
            )
        }

        val timeText = when {
            !startHour.isNullOrBlank() && !endHour.isNullOrBlank()
                    && startHour != endHour -> "$startHour - $endHour"
            !startHour.isNullOrBlank() -> startHour
            !endHour.isNullOrBlank() -> endHour
            else -> null
        }
        if (timeText != null) {
            timeTimeText.append("$timeText")
        }
        if(timeText.isNullOrBlank()){
            timeTimeText.visibility = View.GONE
        }

        //Расписание
        val schedule = eventDates?.schedules?.firstOrNull()
        val daysOfWeek = schedule?.schedules?.joinToString(", ") {
            when (it) {
                1 -> "Пн"
                2 -> "Вт"
                3 -> "Ср"
                4 -> "Чт"
                5 -> "Пт"
                6 -> "Сб"
                7 -> "Вс"
                else -> ""
            }
        }

        if (!daysOfWeek.isNullOrEmpty()) {
            scheduleText.text = "${getString(R.string.event_daysPlace)} $daysOfWeek"
        }
        else{
            scheduleText.visibility = View.GONE
        }




        locationCityText.text = event.location?.name

        val place = event.place?.title
        if (place != null) {
            val subway = event.place?.subway
            val address = event.place?.address

            val locationText = buildString {
                append("$place\n\n")
                append(getString(R.string.event_location))

                if (!subway.isNullOrEmpty()) {
                    append(" ")
                    append(getString(R.string.event_subway))
                    append(" $subway")
                }

                if (!address.isNullOrEmpty()) {
                    if (!subway.isNullOrEmpty()) append(", ") else append(" ")
                    append(address)
                }
            }

            locationDetailedText.text = locationText
        } else {
            locationDetailedText.text = "${getString(R.string.event_location)} ${getString(R.string.event_emptyPlace)}"
        }

        if(!event.is_free){
            priceText.text = event.price
        }
        else{
            priceText.text = getString(R.string.event_freePrice)
        }

    }



}