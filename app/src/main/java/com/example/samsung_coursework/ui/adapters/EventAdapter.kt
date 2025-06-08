package com.example.samsung_coursework.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.samsung_coursework.R
import com.example.samsung_coursework.data.retrofit.CategoryTranslatorEvent
import com.example.samsung_coursework.domain.models.Event
import com.example.samsung_coursework.domain.models.EventDate
import java.text.SimpleDateFormat
import java.util.*

class EventAdapter() : ListAdapter<Event, EventAdapter.EventViewHolder>(DiffCallback()) {

    interface ClickInterface{
        fun onClick(event: Event)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    var clickListener: ClickInterface? = null//клики
    inner class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val titleTextView = view.findViewById<TextView>(R.id.home_eventCardTitle)
        private val dateTextView = view.findViewById<TextView>(R.id.home_eventCardDate)
        private val ageTextView = view.findViewById<TextView>(R.id.home_eventCardAge)
        private val tagsTextView = view.findViewById<TextView>(R.id.home_eventCardTags)
        private val image = view.findViewById<ImageView>(R.id.home_eventCardImage)

        fun bind(event: Event) {
            val imageURL = event?.images?.firstOrNull()?.url
            Glide.with(itemView.context)
                .load(imageURL)
                //.placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(image)


            // Название события
            titleTextView.text = event.short_title

            // Дата
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
                dateTextView.text = "$add $endTime"
            }
            else dateTextView.text = "$endTime"


            /*
            val formatter = SimpleDateFormat("d MMMM", Locale("ru"))
            val time = event.dates?.first()?.startTime
            var start = time?.let { formatter.format(Date(it * 1000)) }

            val curTime = System.currentTimeMillis() / 1000
            if(event.dates!!.size > 2 || event.dates!!.get(0).startTime!! < curTime){
                start = "${itemView.context.getString(R.string.home_startWithEvent)} $start"}
            dateTextView.text = "$start"

             */

            val textAge = when (event.age_restriction) {
                null, "0", "null" -> "0+"
                else -> "${event.age_restriction}"
            }
            ageTextView.text = textAge

            val translatedCategories = CategoryTranslatorEvent.translateCategory(event.categories)
            tagsTextView.text = translatedCategories






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
            return oldItem.short_title == newItem.short_title &&
                    oldItem.age_restriction == newItem.age_restriction &&
                    oldItem.dates == newItem.dates &&
                    oldItem.categories == newItem.categories
        }
    }
}