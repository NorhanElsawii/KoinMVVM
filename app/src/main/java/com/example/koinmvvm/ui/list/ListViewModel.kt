package com.example.koinmvvm.ui.list

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.koinmvvm.data.remote.entities.ResultsItem
import com.example.koinmvvm.ui.list.paging.ListDataSource
import com.example.koinmvvm.ui.list.paging.ListDataSourceFactory
import com.example.koinmvvm.utils.SingleLiveEvent
import com.example.koinmvvm.utils.Status
import com.example.koinmvvm.utils.base.BaseViewModel

class ListViewModel constructor(private val listRepository: ListRepository) :
    BaseViewModel(listRepository) {

    var pagedList: LiveData<PagedList<ResultsItem>>? = null
    val listStatus = SingleLiveEvent<Status>()
    private lateinit var factory: ListDataSourceFactory

    fun initPagedList() {
        factory = ListDataSourceFactory(listRepository, listStatus)
        pagedList =
            LivePagedListBuilder(factory, ListDataSource.PAGE_SIZE).build()
    }

    fun retry() {
        if (isNetworkConnected())
            factory.retry()
    }

    fun refresh() {
        if (isNetworkConnected())
            factory.refresh()
    }

    override fun onCleared() {
        super.onCleared()
        if (::factory.isInitialized)
            factory.onCleared()
    }
}