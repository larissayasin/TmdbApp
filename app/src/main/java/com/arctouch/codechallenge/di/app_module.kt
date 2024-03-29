package com.arctouch.codechallenge.di

import com.arctouch.codechallenge.repository.MovieRepository
import com.arctouch.codechallenge.repository.MovieRepositoryImpl
import com.arctouch.codechallenge.repository.MoviesKeyedDataSource
import com.arctouch.codechallenge.repository.MoviesKeyedDataSourceFactory
import com.arctouch.codechallenge.viewmodel.details.DetailsViewModel
import com.arctouch.codechallenge.viewmodel.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tmdbModule = module {

    viewModel { HomeViewModel(get(), get()) }
    viewModel { DetailsViewModel(get()) }

    single<MovieRepository>(createdAtStart = true) { MovieRepositoryImpl(get()) }
    single { MoviesKeyedDataSource(get()) }
    factory { MoviesKeyedDataSourceFactory() }

}

val tmdbApp = listOf(remoteModule, tmdbModule)