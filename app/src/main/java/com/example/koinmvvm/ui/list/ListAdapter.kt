package com.example.koinmvvm.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.koinmvvm.R
import com.example.koinmvvm.data.remote.entities.ResultsItem
import com.example.koinmvvm.utils.base.BasePagedListAdapter

class ListAdapter :
    BasePagedListAdapter<ResultsItem>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bind(
            getItem(position)
        )
    }

    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<ResultsItem> =
            object : DiffUtil.ItemCallback<ResultsItem>() {
                override fun areItemsTheSame(
                    oldItem: ResultsItem,
                    newItem: ResultsItem
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: ResultsItem,
                    newItem: ResultsItem
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}