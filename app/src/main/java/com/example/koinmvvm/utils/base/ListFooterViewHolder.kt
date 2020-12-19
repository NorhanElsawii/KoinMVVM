package com.example.koinmvvm.utils.base

import android.view.View
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_paged_list_footer.view.*
import kotlinx.android.synthetic.main.layout_try_again_small.view.*

/**
 * Created by Norhan Elsawi on 23/01/2020.
 */
class ListFooterViewHolder(
    private val view: View,
    private val retry: () -> Unit
) :
    RecyclerView.ViewHolder(view) {

    fun setFooterView(loadState: LoadState) {
        with(view) {
            if (loadState is LoadState.Error) {
                tv_error.text = loadState.error.localizedMessage
            }
            pb_loading.isVisible = loadState is LoadState.Loading
            ll_retry.isVisible = loadState is LoadState.Error
            tv_error.isVisible = loadState is LoadState.Error
            ll_retry.setOnClickListener {
                retry.invoke()
            }
        }
    }
}