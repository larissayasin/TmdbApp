package com.arctouch.codechallenge.viewmodel.details

import androidx.lifecycle.MutableLiveData
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.repository.MovieRepository
import com.arctouch.codechallenge.util.Event
import com.arctouch.codechallenge.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailsViewModel(private val repository: MovieRepository) : BaseViewModel() {
    val event = MutableLiveData<Event<Movie>>()

    fun movie(id: Long) {
        disposable = repository.movie(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    event.value = Event(data = it)
                }, { error ->
                    event.value = Event(error = error)
                })
    }
}