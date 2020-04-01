package com.example.koinmvvm.ui.main

import com.example.koinmvvm.data.local.database.entities.Movie
import com.example.koinmvvm.utils.SingleLiveEvent
import com.example.koinmvvm.utils.Status
import com.example.koinmvvm.utils.base.BaseViewModel
import kotlinx.coroutines.flow.collect

class MainViewModel constructor(private val mainRepository: MainRepository) :
    BaseViewModel(mainRepository) {

    val status = SingleLiveEvent<Status>()
    val localData = SingleLiveEvent<List<Movie>>()
        .also {
            getLocalMovies(it)
        }

    fun getMovies() {
        performNetworkCall({
            mainRepository.getMovies()
        }, status, {
            it?.get(0)?.let { it.overview = "norhan" }

            doInBackground {
                it?.let {
                    mainRepository.saveMovies(it.map { resultItem ->
                        Movie(resultItem.id, resultItem.overview)
                    })
                }
            }
        })
    }

    private fun getLocalMovies(it: SingleLiveEvent<List<Movie>>) = doInBackground {
        mainRepository.getLocalMovies().collect { list ->
            it.postValue(list)
        }
    }

    fun getCurrentLanguage(): String {
        return mainRepository.getCurrentLanguage()
    }

    fun changeLanguage() {
       mainRepository.changeLanguage()
    }
}