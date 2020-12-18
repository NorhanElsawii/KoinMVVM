package com.example.koinmvvm.utils.base

import androidx.paging.PagingSource


/**
 * Created by Norhan Elsawi on 23/01/2020.
 */
abstract class BaseDataSource<I : Any>(
    private val repository: BaseRepository
) : PagingSource<Int, I>()