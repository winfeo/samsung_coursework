package com.example.samsung_coursework.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.samsung_coursework.R
import com.example.samsung_coursework.domain.models.Event
import com.example.samsung_coursework.domain.models.SearchedPlace
import com.example.samsung_coursework.ui.adapters.SearchedPlaceAdapter
import com.example.samsung_coursework.ui.view_model.FavoriteViewModel
import com.example.samsung_coursework.ui.view_model.SelectedPlaceViewModel


class FragmentFavoritePlaces : Fragment() {
    private val viewModel: FavoriteViewModel by activityViewModels()
    private val selectedPlaceViewModel: SelectedPlaceViewModel by activityViewModels()
    private val adapter = SearchedPlaceAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite_places, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val clickListener = object: SearchedPlaceAdapter.ClickInterface {
            override fun onClick(place: SearchedPlace) {
                selectedPlaceViewModel.chosePlace(place)
                findNavController().navigate(R.id.action_fragmentFavorite_to_fragmentPlace)
            }

            override fun onFavoriteClick(place: SearchedPlace, isCurrentlyFavorite: Boolean) {
                if (isCurrentlyFavorite) {
                    viewModel.deleteFavoritePlace(place.id)
                } else {
                    viewModel.addFavoritePlace(place.id)
                }
            }
        }
        adapter.clickListener = clickListener

        val recyclerView = view.findViewById<RecyclerView>(R.id.favorite_recyclerViewPlaces)
        recyclerView.adapter = adapter

        viewModel.favoritePlaces.observe(viewLifecycleOwner, Observer { places ->
            adapter.submitList(places)
        })

        viewModel.favoritePlaceIds.observe(viewLifecycleOwner) { ids ->
            adapter.favoritePlaceIds = ids
        }

    }
}