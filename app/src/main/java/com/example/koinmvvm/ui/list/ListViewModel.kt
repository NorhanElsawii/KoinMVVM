package com.example.koinmvvm.ui.list

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.koinmvvm.data.remote.entities.ResultsItem
import com.example.koinmvvm.ui.list.paging.ListPagingSource
import com.example.koinmvvm.utils.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

class ListViewModel constructor(private val listRepository: ListRepository) :
    BaseViewModel(listRepository) {
    var movies: Flow<PagingData<ResultsItem>>? = null

    fun initPagedList() {
        movies =
            Pager(PagingConfig(pageSize = ListPagingSource.PAGE_SIZE)) {
                ListPagingSource(listRepository)
            }.flow
                .cachedIn(viewModelScope)
    }
}