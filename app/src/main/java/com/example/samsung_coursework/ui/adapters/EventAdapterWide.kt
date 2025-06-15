package com.example.samsung_coursework.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.samsung_coursework.R
import com.example.samsung_coursework.domain.models.Event
import com.example.samsung_coursework.domain.models.EventDate
import java.text.SimpleDateFormat
import java.util.*

class EventAdapterWide : ListAdapter<Event, EventAdapterWide.EventViewHolder>(DiffCallback()) {

    interface ClickInterface{
        fun onClick(event: Event)
        fun onFavoriteClick(event: Event, isCurrentlyFavorite: Boolean)
    }

    var favoriteEventIds: Set<Int> = emptySet()
        set(value){
            if(field != value){
                field = value
                notifyDataSetChanged()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event_wide, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    var clickListener: ClickInterface? = null//клики
    inner class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image = view.findViewById<ImageView>(R.id.search_cardImage)
        private val textTitle = view.findViewById<TextView>(R.id.search_cardTitle)
        private val textPlace = view.findViewById<TextView>(R.id.search_cardPlace)
        private val textAge = view.findViewById<TextView>(R.id.search_cardAge)
        private val textDate = view.findViewById<TextView>(R.id.search_cardDate)
        private val favorite = view.findViewById<ImageButton>(R.id.search_cardAddToFavoriteButton)

        fun bind(event: Event) {
            val imageURL = event?.images?.firstOrNull()?.url
            Glide.with(itemView)
                .load(imageURL)
                //.placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(image)

            val isFavorite = favoriteEventIds.contains(event.id)
            favorite.setImageResource(
                if (isFavorite) R.drawable.ic_favorite_full
                else R.drawable.ic_favorite
            )

            textTitle.text = event?.title

            /** TODO Не всегда Москва **/
            textPlace.text = "Москва" + ", ${event?.place?.title ?: ""}" + ", ${event?.place?.address ?: ""}"

            val age = when (event?.age_restriction) {
                null, "0", "null" -> "0+"
                else -> "${event.age_restriction}"
            }
            textAge.text = age

            val formatter = SimpleDateFormat("d MMMM", Locale("ru"))

            val eventDates: EventDate? = event?.dates
                ?.filter { it.endTimeNumber != null && it.endTimeNumber > System.currentTimeMillis() / 1000 }
                ?.minByOrNull { it.endTimeNumber!! }
            //?.maxByOrNull { it.endTime!! }

            val startTime = eventDates?.startTimeNumber?.let { formatter.format(Date(it * 1000)) }
            val endTime = eventDates?.endTimeNumber?.let { formatter.format(Date(it * 1000)) }
            if(!startTime.equals(endTime)){
                val add = itemView.context.getString(R.string.home_endWithEvent)
                val endTime = eventDates?.endTimeNumber?.let { formatter.format(Date(it * 1000 + 24 * 60 * 60 * 1000)) }
                textDate.text = "$add $endTime"
            }
            else textDate.text = "$endTime"



            favorite.setOnClickListener(){
                clickListener?.onFavoriteClick(event, isFavorite)
            }

            itemView.setOnClickListener(){
                clickListener?.onClick(event)
            }

        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean{
            return oldItem == newItem && oldItem.is_favorite == newItem.is_favorite
        }
    }
}