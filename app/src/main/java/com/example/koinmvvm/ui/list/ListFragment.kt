package com.example.koinmvvm.ui.list

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koinmvvm.R
import com.example.koinmvvm.utils.PagedListFooterType
import com.example.koinmvvm.utils.RetryListener
import com.example.koinmvvm.utils.Status
import com.example.koinmvvm.utils.base.BaseFragment
import com.example.koinmvvm.utils.base.BaseViewModel
import com.example.koinmvvm.utils.extensions.observe
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : BaseFragment() {

    private val viewModel by viewModel<ListViewModel>()
    private var adapter: ListAdapter? = null

    override fun layoutId(): Int = R.layout.fragment_list

    override fun getViewModel(): BaseViewModel? {
        return viewModel
    }

    override fun onViewReady() {
        observe(viewModel.listStatus) {
            when (it) {
                is Status.Loading -> {
                    showDialogLoading()
                }
                is Status.LoadingMore -> handleLoadingMore()
                is Status.Success<*> -> {
                    hideDialogLoading()
                }
                is Status.SuccessLoadingMore -> handleSuccessLoadingMore()
                is Status.Error -> {
                    onError(it) {
                        hideDialogLoading()
                    }
                }
                is Status.ErrorLoadingMore -> onError(it) { handleErrorLoadingMore(it) }
            }
        }
        initPagedList()
    }

    private fun initPagedList() {
        initRecyclerView()
        viewModel.initPagedList()
        observe(viewModel.pagedList) {
            adapter?.submitList(it)
        }
    }

    private fun initRecyclerView() {
        adapter = ListAdapter(object : RetryListener {
            override fun onRetry() {
                viewModel.retry()
            }
        })
        rv_list.layoutManager = LinearLayoutManager(context)
        rv_list.adapter = adapter
    }

    private fun handleLoadingMore() {
        setFooter(PagedListFooterType.Loading)
    }

    private fun handleSuccessLoadingMore() {
        setFooter(PagedListFooterType.None)
    }

    private fun handleErrorLoadingMore(error: Status.ErrorLoadingMore) {
        setFooter(PagedListFooterType.Retry)
        showErrorMsg(error.message)
    }

    private fun setFooter(pagedListFooter: PagedListFooterType) {
        adapter?.setFooter(pagedListFooter)
    }
}