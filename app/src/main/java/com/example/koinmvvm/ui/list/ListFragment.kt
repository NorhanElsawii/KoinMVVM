package com.example.koinmvvm.ui.list

import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koinmvvm.R
import com.example.koinmvvm.utils.base.BaseFragment
import com.example.koinmvvm.utils.base.BaseViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : BaseFragment() {

    private val viewModel by viewModel<ListViewModel>()
    private var adapter: ListAdapter? = null

    override fun layoutId(): Int = R.layout.fragment_list

    override fun getViewModel(): BaseViewModel? {
        return viewModel
    }

    override fun onViewReady() {
        initPagedList()
        btn_retry.setOnClickListener {
            adapter?.retry()
        }
    }

    private fun initPagedList() {
        initRecyclerView()
        viewModel.initPagedList()
        this.lifecycleScope.launch {
            viewModel.movies?.collectLatest {
                adapter?.submitData(it)
            }
        }
    }

    private fun initRecyclerView() {
        adapter = ListAdapter()
        rv_list.layoutManager = LinearLayoutManager(context)
        rv_list.setHasFixedSize(true)
        rv_list.adapter = adapter?.withLoadStateFooter(
            footer = ListLoadStateAdapter { adapter?.retry() }
        )
        adapter?.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            rv_list.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            if (loadState.source.refresh is LoadState.Loading)
                showDialogLoading()
            else
                hideDialogLoading()
            // Show the retry state if initial load or refresh fails.
            btn_retry.isVisible = loadState.source.refresh is LoadState.Error

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.source.refresh as? LoadState.Error
            errorState?.let {
                showErrorMsg(
                    "\uD83D\uDE28 Wooops ${it.error}"
                )
            }
        }
    }
}