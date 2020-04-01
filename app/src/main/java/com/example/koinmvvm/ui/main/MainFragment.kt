package com.example.koinmvvm.ui.main

import com.example.koinmvvm.R
import com.example.koinmvvm.data.remote.entities.ResultsItem
import com.example.koinmvvm.ui.list.ListActivity
import com.example.koinmvvm.utils.Status
import com.example.koinmvvm.utils.base.BaseFragment
import com.example.koinmvvm.utils.base.BaseViewModel
import com.example.koinmvvm.utils.extensions.launchActivity
import com.example.koinmvvm.utils.extensions.observe
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment() {

    private val viewModel by viewModel<MainViewModel>()

    override fun layoutId(): Int = R.layout.fragment_main

    override fun getViewModel(): BaseViewModel? {
        return viewModel
    }

    override fun onViewReady() {
        observe(viewModel.localData) {
            tv_from_database.text = it.toString()
        }

        observe(viewModel.status) {
            when (it) {
                is Status.Loading -> {
                    showDialogLoading()
                }
                is Status.Success<*> -> {
                    hideDialogLoading()
                    tv_from_network.text =
                        "${getString(R.string.data)} ${(it.data as List<ResultsItem>)}"
                }
                is Status.Error -> {
                    onError(it) {
                        hideDialogLoading()
                    }
                }
            }
        }
        viewModel.getMovies()

        tv_current_language.text = viewModel.getCurrentLanguage()

        btn_go_to_list.setOnClickListener {
            context?.launchActivity<ListActivity> { }
        }

        btn_switch_language.setOnClickListener {
            viewModel.changeLanguage()
            restart()
        }
    }

    private fun restart() {
        MainActivity.start(activity)
    }
}