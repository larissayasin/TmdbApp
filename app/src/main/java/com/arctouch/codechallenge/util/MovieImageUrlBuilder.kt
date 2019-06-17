package com.arctouch.codechallenge.util

import com.arctouch.codechallenge.ApiConfig



class MovieImageUrlBuilder {

    fun buildPosterUrl(posterPath: String): String {
        return ApiConfig.POSTER_URL + posterPath + "?api_key=" + ApiConfig.API_KEY
    }

    fun buildBackdropUrl(backdropPath: String): String {
        return ApiConfig.BACKDROP_URL + backdropPath + "?api_key=" + ApiConfig.API_KEY
    }
}
