package com.example.koinmvvm.ui.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.koinmvvm.data.remote.entities.ResultsItem
import kotlinx.android.synthetic.main.item_list.view.*


open class ItemViewHolder(
    private val view: View
) :
    RecyclerView.ViewHolder(view) {

    open fun bind(item: ResultsItem?) {
        with(view) {
            item?.let {
                tv_name.text = it.title
            }
        }
    }
}