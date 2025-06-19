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
import com.example.samsung_coursework.data.retrofit.CategoryTranslatorPlace
import com.example.samsung_coursework.domain.models.SearchedPlace

class SearchedPlaceAdapter() : ListAdapter<SearchedPlace, SearchedPlaceAdapter.PlaceViewHolder>(DiffCallback()) {

    interface ClickInterface{
        fun onClick(place: SearchedPlace)
        fun onFavoriteClick(place: SearchedPlace, isCurrentlyFavorite: Boolean)
    }

    var favoritePlaceIds: Set<Int> = emptySet()
        set(value) {
            if (field != value) {
                field = value
                notifyDataSetChanged()
            }
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
        //private val isFreeTextView = itemView.findViewById<TextView>(R.id.search_card2isFree)
        private val favoriteButton = itemView.findViewById<ImageButton>(R.id.search_card2AddToFavoriteButton)

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

            val isFavorite = favoritePlaceIds.contains(place.id)
            favoriteButton.setImageResource(
                if (isFavorite) R.drawable.ic_favorite_full
                else R.drawable.ic_favorite
            )

            /** TODO подумать на что заменить, т.к. API не возвращает пар-тр isFree **/
            /*
            val translatedCategories = CategoryTranslatorPlace.translateCategory(place.categories)
            tagsTextView.text = translatedCategories

             */

            /*
            if (place.isFree == true) {
                isFreeTextView.visibility = View.VISIBLE
            } else {
                isFreeTextView.visibility = View.GONE
            }

             */

            favoriteButton.setOnClickListener {
                clickListener?.onFavoriteClick(place, isFavorite)
            }

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
