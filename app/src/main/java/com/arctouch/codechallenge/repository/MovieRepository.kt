package com.arctouch.codechallenge.repository

import com.arctouch.codechallenge.ApiConfig
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import io.reactivex.Observable

interface MovieRepository{

    fun upcomingMovies(page : Long) : Observable<UpcomingMoviesResponse>
    fun genres() : Observable<GenreResponse>
    fun movie(id  : Long) : Observable<Movie>
}
class MovieRepositoryImpl(private val api : TmdbApi): MovieRepository{
    override fun movie(id: Long): Observable<Movie> {
        return api.movie(id, ApiConfig.API_KEY, ApiConfig.DEFAULT_LANGUAGE)
    }

    override fun genres(): Observable<GenreResponse> {
        return api.genres(ApiConfig.API_KEY, ApiConfig.DEFAULT_LANGUAGE)
    }

    override fun upcomingMovies(page: Long) : Observable<UpcomingMoviesResponse>{
        return api.upcomingMovies(ApiConfig.API_KEY, ApiConfig.DEFAULT_LANGUAGE, page)
    }



}