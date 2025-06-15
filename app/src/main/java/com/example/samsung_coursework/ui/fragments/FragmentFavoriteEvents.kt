package com.example.samsung_coursework.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.samsung_coursework.R
import com.example.samsung_coursework.domain.models.Event
import com.example.samsung_coursework.ui.adapters.EventAdapterWide
import com.example.samsung_coursework.ui.view_model.FavoriteViewModel
import com.example.samsung_coursework.ui.view_model.SelectedEventViewModel


class FragmentFavoriteEvents : Fragment() {
    private val viewModel: FavoriteViewModel by activityViewModels()
    private val selectedViewModel: SelectedEventViewModel by activityViewModels()
    private val adapter = EventAdapterWide()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val clickListener = object: EventAdapterWide.ClickInterface {
            override fun onClick(event: Event) {
                selectedViewModel.choseEvent(event)
                findNavController().navigate(R.id.action_fragmentFavorite_to_fragmentEvent)
            }

            override fun onFavoriteClick(event: Event, isCurrentlyFavorite: Boolean) {
                if (isCurrentlyFavorite) {
                    viewModel.deleteFavoriteEvent(event.id)
                } else {
                    viewModel.addFavoriteEvent(event.id)
                }
            }
        }
        adapter.clickListener = clickListener

        val recyclerView = view.findViewById<RecyclerView>(R.id.favorite_recyclerViewEvents)
        recyclerView.adapter = adapter

        viewModel.favoriteEvents.observe(viewLifecycleOwner) { events ->
            adapter.submitList(events)
        }

        viewModel.favoriteEventIds.observe(viewLifecycleOwner) { ids ->
            adapter.favoriteEventIds = ids
        }

    }
}