package com.example.koinmvvm.utils.base

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Norhan Elsawi on 23/01/2020.
 */
abstract class BasePagedListAdapter<T : Any>(
    DIFF_CALLBACK: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, RecyclerView.ViewHolder>(
    DIFF_CALLBACK
)
