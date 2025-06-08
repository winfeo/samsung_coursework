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
import com.example.samsung_coursework.domain.models.SearchedPlace

class SearchedPlaceAdapter() : ListAdapter<SearchedPlace, SearchedPlaceAdapter.PlaceViewHolder>(DiffCallback()) {

    interface ClickInterface{
        fun onClick(place: SearchedPlace)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_place, parent, false)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    var clickListener: SearchedPlaceAdapter.ClickInterface? = null//клики
    inner class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView = itemView.findViewById<TextView>(R.id.search_card2Title)
        private val descriptionTextView = itemView.findViewById<TextView>(R.id.search_card2Description)
        private val image = itemView.findViewById<ImageView>(R.id.search_card2Image)
        private val isFreeTextView = itemView.findViewById<TextView>(R.id.search_card2isFree)

        fun bind(place: SearchedPlace) {
            val imageURL = place?.images?.firstOrNull()?.url
            Glide.with(itemView.context)
                .load(imageURL)
                //.placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(image)

            titleTextView.text = place.title.replaceFirstChar { it.uppercaseChar() }
            descriptionTextView.text = place.description?.replaceFirstChar { it.uppercaseChar() }

            /** TODO подумать на что заменить, т.к. API не возвращает пар-тр isFree **/
            /*
            if (place.isFree == true) {
                isFreeTextView.visibility = View.VISIBLE
            } else {
                isFreeTextView.visibility = View.GONE
            }

             */


            itemView.setOnClickListener(){
                clickListener?.onClick(place)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<SearchedPlace>() {
        override fun areItemsTheSame(oldItem: SearchedPlace, newItem: SearchedPlace): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: SearchedPlace, newItem: SearchedPlace): Boolean =
            oldItem == newItem
    }
}
