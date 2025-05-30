package com.example.samsung_coursework.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.samsung_coursework.R
import com.example.samsung_coursework.adapters.EventAdapter
import com.example.samsung_coursework.view_model.EventViewModel

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

        // Загрузить события
        viewModel.loadEvents()
    }
}