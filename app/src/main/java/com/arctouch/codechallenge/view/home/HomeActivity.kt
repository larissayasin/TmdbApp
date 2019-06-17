package com.arctouch.codechallenge.view.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.viewmodel.home.HomeViewModel
import kotlinx.android.synthetic.main.home_activity.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val vm: HomeViewModel by viewModel()

    private val adapter = HomeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        vm.getGenres()
        initializeList()

        vm.genreEvent.observe(this, Observer { genreEvent ->
            when {
                genreEvent.isSuccess -> {
                    vm.getUpcomingMoviesList()
                    observeMovieList()
                }
                genreEvent.error != null -> {
                    Toast.makeText(this, getString(R.string.error_msg), Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                }
            }
        })

        vm.networkStateLiveData?.observe(this, Observer { network ->
            when{
                network.error != null ->  {
                    Toast.makeText(this, getString(R.string.error_msg), Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                }
                !network.isLoading -> progressBar.visibility = View.GONE
            }
        })
    }

    private fun initializeList() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun observeMovieList(){
        vm.movieList.observe(this,
                Observer<PagedList<Movie>> { items ->
                    adapter.submitList(items)
                    progressBar.visibility = View.GONE
                })
    }

    override fun onDestroy() {
        vm.clearDisposable()
        super.onDestroy()

    }
}
