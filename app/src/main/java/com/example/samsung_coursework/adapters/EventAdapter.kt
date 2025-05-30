package com.example.samsung_coursework.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.samsung_coursework.R
import com.example.samsung_coursework.models.retrofit.Event

class EventAdapter : ListAdapter<Event, EventAdapter.EventViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val titleTextView = view.findViewById<TextView>(R.id.title)
        private val dateTextView = view.findViewById<TextView>(R.id.date)
        private val placeTextView = view.findViewById<TextView>(R.id.place)
        //private val imageView = view.findViewById<ImageView>(R.id.image)

        fun bind(event: Event) {

        }
    }

    //сравнение старых и новых данных при обновлениии списка
    class DiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Event, newItem: Event) = oldItem == newItem
    }
}