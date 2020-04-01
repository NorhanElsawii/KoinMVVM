package com.example.koinmvvm.utils.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.koinmvvm.utils.PagedListFooterType
import com.example.koinmvvm.utils.RetryListener
import kotlinx.android.synthetic.main.item_paged_list_footer.view.*
import kotlinx.android.synthetic.main.layout_try_again_small.view.*

/**
 * Created by Norhan Elsawi on 23/01/2020.
 */
abstract class BaseListFooterViewHolder(
    private val view: View,
    private val retryListener: RetryListener
) :
    RecyclerView.ViewHolder(view) {

    abstract fun getPagedListFooterType(): PagedListFooterType

    fun setFooterView() {
        with(view) {
            if (getPagedListFooterType() is PagedListFooterType.Retry) {
                ll_retry.visibility = View.VISIBLE
                pb_loading.visibility = View.GONE
            } else {
                ll_retry.visibility = View.GONE
                pb_loading.visibility = View.VISIBLE
            }

            ll_retry.setOnClickListener {
                retryListener.onRetry()
            }

        }
    }
}