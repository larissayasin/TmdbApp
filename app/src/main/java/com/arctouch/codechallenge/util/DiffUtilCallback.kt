package com.arctouch.codechallenge.util

import androidx.recyclerview.widget.DiffUtil
import com.arctouch.codechallenge.model.Movie

class DiffUtilCallBack : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.title == newItem.title
                && oldItem.overview == newItem.overview
                && oldItem.releaseDate == newItem.releaseDate
    }

}