package com.arctouch.codechallenge.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.GenericEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MoviesKeyedDataSource(private val repository: MovieRepository) : PageKeyedDataSource<Int, Movie>() {
    private var currentPage = 1
    val networkEvent = MutableLiveData<GenericEvent<Void>>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        networkEvent.postValue(GenericEvent(isLoading = true))
        val d = repository.upcomingMovies(currentPage.toLong())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ moviesResponse ->
                    val moviesWithGenres = moviesResponse.results.map { movie ->
                        movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                    }
                    callback.onResult(moviesWithGenres, 0, 2)
                    networkEvent.postValue(GenericEvent(isLoading = false))
                }, { error ->
                    networkEvent.postValue(GenericEvent(error = error))
                })

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        currentPage++
        networkEvent.postValue(GenericEvent(isLoading = true))
        val d = repository.upcomingMovies(currentPage.toLong())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ moviesResponse ->
                    val moviesWithGenres = moviesResponse.results.map { movie ->
                        movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                    }
                    callback.onResult(moviesWithGenres, currentPage)
                    networkEvent.postValue(GenericEvent(isLoading = false))
                }, { error ->
                    networkEvent.postValue(GenericEvent(error = error))
                })

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
    }

}