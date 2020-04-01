package com.example.koinmvvm.utils.base

import androidx.paging.DataSource


/**
 * Created by Norhan Elsawi on 23/01/2020.
 */
abstract class BaseDataSourceFactory<T, D : BaseDataSource<T>> : DataSource.Factory<Int, T>() {
    private var dataSource: D? = null

    abstract fun getDataSource(): D

    override fun create(): DataSource<Int, T> {
        dataSource = getDataSource()
        return dataSource!!
    }

    fun retry() {
        dataSource?.retry()
    }

    fun refresh() {
        dataSource?.invalidate()
    }

    fun onCleared() {
        dataSource?.onCleared()
    }
}