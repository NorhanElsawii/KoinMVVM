package com.example.koinmvvm.ui.list

import com.example.koinmvvm.data.remote.apicalls.MovieApiCalls
import com.example.koinmvvm.utils.base.BaseRepository

class ListRepository(private val movieApiCalls: MovieApiCalls) :
    BaseRepository() {

    suspend fun getMovies(page: Int) = movieApiCalls.getMovies(page)
}
