package com.example.koinmvvm.di

import com.example.koinmvvm.ui.list.ListViewModel
import com.example.koinmvvm.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


/**
 * Created by Norhan Elsawi on 23/01/2020.
 */
val viewModelModule = module {

    viewModel { MainViewModel(get()) }
    viewModel { ListViewModel(get()) }
}