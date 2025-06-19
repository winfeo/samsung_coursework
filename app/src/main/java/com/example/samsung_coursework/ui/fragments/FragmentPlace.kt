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
import com.example.samsung_coursework.data.retrofit.CategoryTranslatorPlace
import com.example.samsung_coursework.domain.models.SearchedPlace
import com.example.samsung_coursework.ui.view_model.FavoriteViewModel
import com.example.samsung_coursework.ui.view_model.SelectedPlaceViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class FragmentPlace : Fragment() {
    private lateinit var toolbar: Toolbar
    private val selectedPlaceViewModel: SelectedPlaceViewModel by activityViewModels()
    private val favoriteViewModel: FavoriteViewModel by activityViewModels()
    private lateinit var favoriteButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_menu)
        bottomNav.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.place_toolbar)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener{
            findNavController().navigateUp()
        }

        val window = requireActivity().window
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        WindowInsetsControllerCompat(window, view).isAppearanceLightStatusBars = false

        favoriteButton = view.findViewById<ImageButton>(R.id.place_favorite_button)

        selectedPlaceViewModel.place.observe(viewLifecycleOwner) { place ->
            updateCardInfo(place, view)
            selectedPlaceViewModel.updateFavoriteStatus(favoriteViewModel.isFavoritePlace(place.id))
        }

        selectedPlaceViewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            favoriteButton.setImageResource(
                if (isFavorite) R.drawable.ic_favorite_full
                else R.drawable.ic_favorite
            )
        }


        val descriptionContainer = view.findViewById<LinearLayout>(R.id.description_containerPlace)
        val descriptionContent = view.findViewById<LinearLayout>(R.id.description_contentPlace)
        val descriptionText = view.findViewById<TextView>(R.id.description_textPlace)
        val descriptionArrow = view.findViewById<ImageView>(R.id.description_arrowPlace)
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
            selectedPlaceViewModel.place.value?.id?.let { placeId ->
                val currentlyFavorite = selectedPlaceViewModel.isFavorite.value ?: false
                if (currentlyFavorite) {
                    favoriteViewModel.deleteFavoritePlace(placeId)
                } else {
                    favoriteViewModel.addFavoritePlace(placeId)
                }
                selectedPlaceViewModel.workFavorite(placeId)
            }
        }

    }

    private fun updateCardInfo(place: SearchedPlace, view: View){
        val imageView = view.findViewById<ImageView>(R.id.place_image)
        val categoriesText = view.findViewById<TextView>(R.id.place_categories)
        val titleText = view.findViewById<TextView>(R.id.place_title)
        val descriptionText = view.findViewById<TextView>(R.id.description_textPlace)
        val scheduleText = view.findViewById<TextView>(R.id.place_schedule)
        val locationText = view.findViewById<TextView>(R.id.place_locationCity)
        val locationDetailedText = view.findViewById<TextView>(R.id.place_locationDetailed)
        val contactsLink = view.findViewById<TextView>(R.id.place_link)
        val contactsPhone = view.findViewById<TextView>(R.id.place_phone)



        val imageURL = place?.images?.firstOrNull()?.url
        Glide.with(view)
            .load(imageURL)
            .error(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .into(imageView)

        val translatedCategories = CategoryTranslatorPlace.translateCategory(place.categories)
        categoriesText.text = translatedCategories

        titleText.text = place?.title
        descriptionText.text = place.bodyText

        if(!place.timetable.isNullOrBlank()){
            scheduleText.text = place.timetable
        }
        else{
            val timeContainer = view.findViewById<LinearLayout>(R.id.place_timeContainer)
            timeContainer.visibility = View.GONE
        }

        val cityName = mapOf(
            "msk" to "Москва",
            "spb" to "Санкт-Петербург",
            "ekb" to "Екатеринбург",
            "kzn" to "Казань",
            "nnv" to "Нижний Новгород"
        )
        val city = cityName[place.location]
        locationText.text = city

        if(!place.address.isNullOrBlank()){
            locationDetailedText.text = place.address
        }

        if(!place.foreignUrl.isNullOrBlank()){
            contactsLink.text = place.foreignUrl
        }
        else{
            contactsLink.visibility = View.GONE
        }

        if(!place.phone.isNullOrBlank()){
            contactsPhone.text =place.phone
        }
        else{
            contactsPhone.visibility = View.GONE
        }


        /*
        val isFavorite = favoriteViewModel.isFavorite(place.id)
        favoriteButton.setImageResource(
            if (isFavorite) R.drawable.ic_favorite_full
            else R.drawable.ic_favorite
        )

         */
    }

}