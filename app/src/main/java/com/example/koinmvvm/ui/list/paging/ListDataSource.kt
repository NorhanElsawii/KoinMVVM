package com.example.koinmvvm.ui.list.paging

import com.example.koinmvvm.data.remote.entities.ResultsItem
import com.example.koinmvvm.ui.list.ListRepository
import com.example.koinmvvm.utils.SingleLiveEvent
import com.example.koinmvvm.utils.Status
import com.example.koinmvvm.utils.base.BaseDataSource

class ListDataSource(
    private val repository: ListRepository,
    status: SingleLiveEvent<Status>
) :
    BaseDataSource<ResultsItem>(repository, status) {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ResultsItem>
    ) {
        performNetworkCall(
            { repository.getMovies(FIRST_PAGE) },
            { loadInitial(params, callback) },
            {
                callback.onResult(it ?: ArrayList(), null, FIRST_PAGE + 1)
            },
            false
        )
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ResultsItem>
    ) {

        performNetworkCall(
            { repository.getMovies(params.key.toInt()) },
            { loadAfter(params, callback) },
            {
                callback.onResult(it ?: ArrayList(), params.key.toInt() + 1)
            },
            true
        )
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ResultsItem>
    ) {
        //pass
    }

    companion object {
        const val FIRST_PAGE = 1
        const val PAGE_SIZE = 10
    }
}