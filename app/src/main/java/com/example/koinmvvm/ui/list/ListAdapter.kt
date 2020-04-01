package com.example.koinmvvm.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.koinmvvm.R
import com.example.koinmvvm.data.remote.entities.ResultsItem
import com.example.koinmvvm.utils.RetryListener
import com.example.koinmvvm.utils.base.BasePagedListAdapter

class ListAdapter(
    private val retryListener: RetryListener
) :
    BasePagedListAdapter<ResultsItem>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LIST_VIEW_TYPE -> ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_list,
                    parent,
                    false
                )
            )
            else -> FooterViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_paged_list_footer,
                    parent,
                    false
                ), retryListener
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when {
            getItemViewType(position) == FOOTER_VIEW_TYPE -> (holder as BasePagedListAdapter<*>.FooterViewHolder).setFooterView()
            getItemViewType(position) == LIST_VIEW_TYPE -> (holder as ItemViewHolder).bind(
                getItem(
                    position
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getActualListItemCount() && getItem(position) != null)
            LIST_VIEW_TYPE
        else
            FOOTER_VIEW_TYPE
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

        const val FOOTER_VIEW_TYPE = 1
        const val LIST_VIEW_TYPE = 3
    }
}