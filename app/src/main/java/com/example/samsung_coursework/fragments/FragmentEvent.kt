package com.example.samsung_coursework.fragments

import android.animation.LayoutTransition
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
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.samsung_coursework.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class FragmentEvent : Fragment() {
    private lateinit var toolbar: Toolbar

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





        val descriptionContainer = view.findViewById<LinearLayout>(R.id.description_container)
        val descriptionContent = view.findViewById<LinearLayout>(R.id.description_content)
        val descriptionText = view.findViewById<TextView>(R.id.description_text)
        val descriptionArrow = view.findViewById<ImageView>(R.id.description_arrow)
        var initialHeight = descriptionContent.height
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
                // Expand
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
                // Collapse
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
}