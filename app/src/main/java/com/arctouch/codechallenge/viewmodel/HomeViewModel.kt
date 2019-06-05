package com.arctouch.codechallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.repository.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {

    val movieEvent = MutableLiveData<MovieGenreEvent>()
    val genreEvent = MutableLiveData<GenreEvent>()

    fun getUpcomingMoviews() {
        movieEvent.value = MovieGenreEvent(isLoading = true)
        repository.upcomingMovies(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val moviesWithGenres = it.results.map { movie ->
                        movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                    }
                    movieEvent.value = MovieGenreEvent(movies = moviesWithGenres)
                }, { error ->
                    movieEvent.value = MovieGenreEvent(error = error)
                })
    }

    fun getGenres() {
        repository.genres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    genreEvent.value = GenreEvent(isSuccess = true)
                    Cache.cacheGenres(it.genres)
                }, { error ->
                    genreEvent.value = GenreEvent(error = error)
                })

    }

}

data class GenreEvent(val isSuccess: Boolean = false, val error: Throwable? = null)
data class MovieGenreEvent(val isLoading: Boolean = false, val error: Throwable? = null, val movies: List<Movie>? = null)