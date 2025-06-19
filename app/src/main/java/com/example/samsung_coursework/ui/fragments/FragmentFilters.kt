package com.example.samsung_coursework.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.samsung_coursework.R
import com.example.samsung_coursework.domain.models.FilterOptions
import com.example.samsung_coursework.ui.view_model.SearchViewModel
import com.google.android.material.chip.ChipGroup
import com.google.android.material.switchmaterial.SwitchMaterial

class FragmentFilters : Fragment() {
    private val searchViewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_filters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contentTypeGroup = view.findViewById<ChipGroup>(R.id.filter_contentTypeGroup)
        val isFreeSwitch = view.findViewById<SwitchMaterial>(R.id.filter_isFree)
        val citySpinner = view.findViewById<Spinner>(R.id.filter_citySpinner)
        val sortGroup = view.findViewById<RadioGroup>(R.id.filter_sortGroup)
        val showGroup = view.findViewById<RadioGroup>(R.id.filter_showGroup)
        val applyButton = view.findViewById<Button>(R.id.filter_applyButton)
        val resetButton = view.findViewById<Button>(R.id.filter_resetButton)

        val displayCities = resources.getStringArray(R.array.pref_cityList)
        val apiCities = resources.getStringArray(R.array.home_cityListAPI)
        val cityMap = displayCities.zip(apiCities).toMap()

        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            displayCities
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            citySpinner.adapter = adapter
        }

        applyButton.setOnClickListener {
            applyFilters(
                contentTypeGroup = contentTypeGroup,
                citySpinner = citySpinner,
                cityMap = cityMap,
                isFreeSwitch = isFreeSwitch,
                sortGroup = sortGroup,
                showGroup = showGroup
            )
        }

        resetButton.setOnClickListener {
            resetFilters(
                contentTypeGroup = contentTypeGroup,
                citySpinner = citySpinner,
                isFreeSwitch = isFreeSwitch,
                sortGroup = sortGroup,
                showGroup = showGroup
            )
        }

        searchViewModel.currentFilters.value?.let { filters ->
            restoreFilterState(
                contentTypeGroup = contentTypeGroup,
                citySpinner = citySpinner,
                isFreeSwitch = isFreeSwitch,
                sortGroup = sortGroup,
                showGroup = showGroup,
                filters = filters,
                cityMap = cityMap,
                displayCities = displayCities
            )
        }

    }


    private fun applyFilters(
        contentTypeGroup: ChipGroup,
        citySpinner: Spinner,
        cityMap: Map<String, String>,
        isFreeSwitch: SwitchMaterial,
        sortGroup: RadioGroup,
        showGroup: RadioGroup
    ) {
        val selectedContentType = when (contentTypeGroup.checkedChipId) {
            R.id.filter_contentTypePlaces -> "place"
            else -> "event"
        }

        val selectedDisplayCity = citySpinner.selectedItem.toString()
        val location = cityMap[selectedDisplayCity] ?: "msk"

        val isFree = isFreeSwitch.isChecked

        val orderBy = when (sortGroup.checkedRadioButtonId) {
            R.id.filter_sortByDate -> "-id"
            else -> "-favorites_count"
        }

        val pageSize = when (showGroup.checkedRadioButtonId) {
            R.id.filter_show5 -> 5
            R.id.filter_show20 -> 20
            R.id.filter_show30 -> 30
            else -> 10
        }

        val filters = FilterOptions(
            searchType = selectedContentType,
            location = location,
            isFree = isFree,
            orderBy = orderBy,
            pageSize = pageSize
        )

        searchViewModel.applyFilters(filters)
        findNavController().navigateUp()
    }

    private fun resetFilters(
        contentTypeGroup: ChipGroup,
        citySpinner: Spinner,
        isFreeSwitch: SwitchMaterial,
        sortGroup: RadioGroup,
        showGroup: RadioGroup
    ) {
        contentTypeGroup.check(R.id.filter_contentTypeEvents)
        citySpinner.setSelection(0)
        isFreeSwitch.isChecked = false
        sortGroup.check(R.id.filter_sortByPopularity)
        showGroup.check(R.id.filter_show10)
        findNavController().navigateUp()
    }

    private fun restoreFilterState(
        contentTypeGroup: ChipGroup,
        citySpinner: Spinner,
        isFreeSwitch: SwitchMaterial,
        sortGroup: RadioGroup,
        showGroup: RadioGroup,
        filters: FilterOptions,
        cityMap: Map<String, String>,
        displayCities: Array<String>
    ) {
        // Восстановление типа контента
        when (filters.searchType) {
            "place" -> contentTypeGroup.check(R.id.filter_contentTypePlaces)
            else -> contentTypeGroup.check(R.id.filter_contentTypeEvents)
        }

        // Восстановление города
        val displayCity = cityMap.entries.find { it.value == filters.location }?.key
        val position = displayCities.indexOf(displayCity).takeIf { it >= 0 } ?: 0
        citySpinner.setSelection(position)

        // Восстановление цены
        isFreeSwitch.isChecked = filters.isFree?: false

        // Восстановление сортировки
        when (filters.orderBy) {
            "-id" -> sortGroup.check(R.id.filter_sortByDate)
            else -> sortGroup.check(R.id.filter_sortByPopularity)
        }

        // Восстановление количества
        when (filters.pageSize) {
            5 -> showGroup.check(R.id.filter_show5)
            20 -> showGroup.check(R.id.filter_show20)
            30 -> showGroup.check(R.id.filter_show30)
            else -> showGroup.check(R.id.filter_show10)
        }
    }
}