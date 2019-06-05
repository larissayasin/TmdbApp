package com.arctouch.codechallenge.view.details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.arctouch.codechallenge.viewmodel.details.DetailsViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.home_activity.*
import kotlinx.android.synthetic.main.movie_item.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity() {

    private val vm: DetailsViewModel by viewModel()
    private val movieImageUrlBuilder = MovieImageUrlBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val idMovie = intent.getLongExtra("id", 0)
        vm.movie(idMovie)

        vm.event.observe(this, Observer { event ->
            if (event != null) {
                when {
                    event.isLoading -> progressBar.visibility = View.VISIBLE
                    event.data != null && event.data is Movie -> {
                        showData(event.data)
                    }
                    event.error != null -> {
                        Toast.makeText(this, getString(R.string.error_msg), Toast.LENGTH_SHORT).show()

                    }
                }
            }
        })
    }

    private fun showData(movie: Movie) {
        tv_details_overview.text = movie.overview
        tv_details_title.text = movie.title
        tv_details_genre.text = movie.genres?.joinToString(separator = ", ") { it.name }

        tv_details_release_date.text = movie.releaseDate

        Glide.with(this)
                .load(movie.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(iv_details_poster)

        Glide.with(this)
                .load(movie.backdropPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(iv_details_backdrop)
    }

    override fun onDestroy() {
        vm.clearDisposable()
        super.onDestroy()
    }
}
