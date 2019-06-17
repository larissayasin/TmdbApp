package com.arctouch.codechallenge.repository

import androidx.paging.DataSource
import com.arctouch.codechallenge.model.Movie
import androidx.lifecycle.MutableLiveData
import org.koin.core.KoinComponent
import org.koin.core.inject


class MoviesKeyedDataSourceFactory : DataSource.Factory<Int, Movie>(){

    private val itemLiveDataSource = MutableLiveData<MoviesKeyedDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val itemDataSource : MoviesKeyedDataSource = getKoinInstance()
        itemLiveDataSource.postValue(itemDataSource)
        return itemDataSource
    }

    fun getItemLiveDataSource(): MutableLiveData<MoviesKeyedDataSource> {
        return itemLiveDataSource
    }

    private inline fun <reified T : Any> getKoinInstance(): T {
        return object : KoinComponent {
            val value: T by inject()
        }.value
    }
}