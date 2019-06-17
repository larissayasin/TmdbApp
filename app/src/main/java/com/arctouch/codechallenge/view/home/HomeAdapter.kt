package com.arctouch.codechallenge.view.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.DiffUtilCallBack
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.arctouch.codechallenge.view.details.DetailsActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_item.view.*

class HomeAdapter : PagedListAdapter<Movie, MovieViewHolder>(DiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder:MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}
class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val movieImageUrlBuilder = MovieImageUrlBuilder()

    fun bind(movie: Movie) {
        itemView.titleTextView.text = movie.title
        itemView.genresTextView.text = movie.genres?.joinToString(separator = ", ") { it.name }
        itemView.releaseDateTextView.text = movie.releaseDate

        Glide.with(itemView)
                .load(movie.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(itemView.posterImageView)

        itemView.setOnClickListener {
            val i = Intent(itemView.context, DetailsActivity::class.java)
            i.putExtra("id", movie.id)
            itemView.context.startActivity(i)
        }
    }
}