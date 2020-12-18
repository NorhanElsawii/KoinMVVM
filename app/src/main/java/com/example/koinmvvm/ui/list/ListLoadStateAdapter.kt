package com.example.koinmvvm.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.example.koinmvvm.R
import com.example.koinmvvm.utils.base.ListFooterViewHolder

class ListLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<ListFooterViewHolder>() {

    override fun onBindViewHolder(holder: ListFooterViewHolder, loadState: LoadState) {
        holder.setFooterView(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ListFooterViewHolder {
        return ListFooterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_paged_list_footer,
                parent,
                false
            ), retry
        )
    }
}