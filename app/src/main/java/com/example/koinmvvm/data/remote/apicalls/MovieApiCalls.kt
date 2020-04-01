package com.example.koinmvvm.data.remote.apicalls

import com.example.koinmvvm.data.remote.api.MovieApi

class MovieApiCalls(private val movieApi: MovieApi) {
    suspend fun getMovies(page: Int) = movieApi.executeGetMovieList(page)
}