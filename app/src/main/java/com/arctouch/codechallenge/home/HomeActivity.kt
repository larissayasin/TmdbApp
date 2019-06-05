package com.arctouch.codechallenge.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_activity.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val vm: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        vm.getGenres()
        vm.genreEvent.observe(this, Observer { genreEvent ->
            when {
                genreEvent.isSuccess ->
                    vm.getUpcomingMoviews()
                genreEvent.error != null -> {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                }
            }
        })
        vm.movieEvent.observe(this, Observer { event ->
            if (event != null) {
                when {
                    event.isLoading -> progressBar.visibility = View.VISIBLE
                    event.movies != null -> {
                        recyclerView.adapter = HomeAdapter(event.movies)
                        progressBar.visibility = View.GONE
                    }
                    event.error != null -> {
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                        progressBar.visibility = View.GONE
                    }
                }
            }
        })
    }
}
