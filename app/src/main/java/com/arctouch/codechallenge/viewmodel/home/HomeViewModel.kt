package com.arctouch.codechallenge.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.repository.MovieRepository
import com.arctouch.codechallenge.repository.MoviesKeyedDataSource
import com.arctouch.codechallenge.repository.MoviesKeyedDataSourceFactory
import com.arctouch.codechallenge.util.GenericEvent
import com.arctouch.codechallenge.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val factory: MoviesKeyedDataSourceFactory, private val repository: MovieRepository) : BaseViewModel() {
    val genreEvent = MutableLiveData<GenreEvent>()

    var networkStateLiveData: LiveData<GenericEvent<Void>>? = null

    lateinit var movieList: LiveData<PagedList<Movie>>

    private var liveDataSource: LiveData<MoviesKeyedDataSource> = factory.getItemLiveDataSource()

    fun getUpcomingMoviesList(){
        networkStateLiveData = Transformations.switchMap(
                liveDataSource
        ) { input -> input!!.networkEvent }

        val pagedListConfig = PagedList.Config.Builder().setEnablePlaceholders(true)
                .setPageSize(10)
                .build()

        movieList = LivePagedListBuilder(factory, pagedListConfig).build()
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

class GenreEvent(val isSuccess: Boolean = false, genreError: Throwable? = null) : GenericEvent<Void>(error = genreError)