package com.example.koinmvvm.ui.main

import com.example.koinmvvm.data.local.database.dao.MovieDao
import com.example.koinmvvm.data.local.database.entities.Movie
import com.example.koinmvvm.data.remote.apicalls.MovieApiCalls
import com.example.koinmvvm.utils.base.BaseRepository
import com.example.koinmvvm.utils.locale.LocaleLanguage

class MainRepository(private val movieApiCalls: MovieApiCalls, private val movieDao: MovieDao) :
    BaseRepository() {

    suspend fun getMovies() = movieApiCalls.getMovies(1)

    suspend fun saveMovies(movies: List<Movie>) = movieDao.addAll(movies)

    fun getLocalMovies() = movieDao.getAll()

    fun getCurrentLanguage(): String {
        return localDataUtils.sharedPreferencesUtils.getLanguage()
    }

    fun changeLanguage() {
        if (localDataUtils.sharedPreferencesUtils.getLanguage() == LocaleLanguage.Arabic.getId())
            localDataUtils.sharedPreferencesUtils.setLanguage(LocaleLanguage.English.getId())
        else
            localDataUtils.sharedPreferencesUtils.setLanguage(LocaleLanguage.Arabic.getId())

    }
}