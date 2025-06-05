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
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.samsung_coursework.R
import com.example.samsung_coursework.data.retrofit.CategoryTranslator
import com.example.samsung_coursework.domain.models.Event
import com.example.samsung_coursework.domain.models.EventDate
import com.example.samsung_coursework.ui.view_model.SelectedEventViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*

class FragmentEvent : Fragment() {
    private lateinit var toolbar: Toolbar
    private val selectedEventViewModel: SelectedEventViewModel by activityViewModels()

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
        //toolbar.title = "Событие дня"

        val window = requireActivity().window
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        WindowInsetsControllerCompat(window, view).isAppearanceLightStatusBars = false



        selectedEventViewModel.event.observe(viewLifecycleOwner){ event ->
            updateCardInfo(event, view)
        }


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


    }

    fun updateCardInfo(event: Event, view: View){
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

        val imageURL = event?.images?.firstOrNull()?.url
        Glide.with(view)
            .load(imageURL)
            .error(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .into(imageView)

        val translatedCategories = CategoryTranslator.translateCategory(event.categories)
        categoriesText.text = translatedCategories

        val textAge = when (event.age_restriction) {
            null, "0", "null" -> "0+"
            else -> "${event.age_restriction}"
        }
        ageText.text = textAge

        titleText.text = event?.title
        descriptionText.text = event?.body_text //description?

        val formatter = SimpleDateFormat("d MMMM", Locale("ru"))
        val eventDates: EventDate? = event?.dates
            ?.filter { it.endTime != null && it.endTime > System.currentTimeMillis() / 1000 }
            ?.minByOrNull { it.endTime!! }
        //?.maxByOrNull { it.endTime!! }
        val startTime = eventDates?.startTime?.let { formatter.format(Date(it * 1000)) }
        val endTime = eventDates?.endTime?.let { formatter.format(Date(it * 1000)) }
        if(!startTime.equals(endTime)){
            val add = view.context.getString(R.string.home_endWithEvent) //?
            val endTime = eventDates?.endTime?.let { formatter.format(Date(it * 1000 + 24 * 60 * 60 * 1000)) }
            timeDateText.text = "$add $endTime"
        }
        else timeDateText.text = "$endTime"

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