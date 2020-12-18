package com.example.koinmvvm.ui.list.paging

import com.example.koinmvvm.data.remote.entities.ResultsItem
import com.example.koinmvvm.ui.list.ListRepository
import com.example.koinmvvm.utils.base.BaseDataSource

class ListPagingSource(
    private val repository: ListRepository
) :
    BaseDataSource<ResultsItem>(repository) {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
        return try {
            val page = params.key ?: FIRST_PAGE
            val response = repository.getMovies(page)
            val list = response.body()?.data
            LoadResult.Page(
                data = list ?: arrayListOf(),
                prevKey = if (page == FIRST_PAGE) null else page - 1,
                nextKey = if (list.isNullOrEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val FIRST_PAGE = 1
        const val PAGE_SIZE = 10
    }
}