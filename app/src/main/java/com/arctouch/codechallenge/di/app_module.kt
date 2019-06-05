package com.arctouch.codechallenge.di

import com.arctouch.codechallenge.repository.MovieRepository
import com.arctouch.codechallenge.repository.MovieRepositoryImpl
import com.arctouch.codechallenge.viewmodel.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tmdbModule = module {

    viewModel { HomeViewModel(get()) }

    single<MovieRepository>(createdAtStart = true) { MovieRepositoryImpl(get()) }
}

val tmdbApp = listOf(remoteModule, tmdbModule)