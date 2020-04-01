package com.example.koinmvvm.ui.list.paging

import com.example.koinmvvm.data.remote.entities.ResultsItem
import com.example.koinmvvm.ui.list.ListRepository
import com.example.koinmvvm.utils.SingleLiveEvent
import com.example.koinmvvm.utils.Status
import com.example.koinmvvm.utils.base.BaseDataSourceFactory

class ListDataSourceFactory(
    private val repository: ListRepository,
    private val status: SingleLiveEvent<Status>
) : BaseDataSourceFactory<ResultsItem, ListDataSource>() {

    override fun getDataSource(): ListDataSource {
        return ListDataSource(repository, status)
    }
}