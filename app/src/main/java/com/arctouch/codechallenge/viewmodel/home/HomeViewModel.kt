package com.arctouch.codechallenge.viewmodel.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.repository.MovieRepository
import com.arctouch.codechallenge.util.GenericEvent
import com.arctouch.codechallenge.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val repository: MovieRepository) : BaseViewModel() {

    val movieEvent = MutableLiveData<GenericEvent>()
    val genreEvent = MutableLiveData<GenreEvent>()

    fun getUpcomingMoviews() {
        movieEvent.value = GenericEvent(isLoading = true)
        disposable = repository.upcomingMovies(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val moviesWithGenres = it.results.map { movie ->
                        movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                    }
                    movieEvent.value = GenericEvent(data = moviesWithGenres)
                }, { error ->
                    movieEvent.value = GenericEvent(error = error)
                })

    }

    fun getGenres() {
        disposable = repository.genres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    genreEvent.value = GenreEvent(isSuccess = true)
                    Cache.cacheGenres(it.genres)
                }, { error ->
                    genreEvent.value = GenreEvent(genreError = error)
                })
    }

}

class GenreEvent(val isSuccess: Boolean = false, genreError: Throwable? = null) : GenericEvent(error = genreError)