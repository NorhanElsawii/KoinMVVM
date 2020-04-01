package com.example.koinmvvm.di

import com.example.koinmvvm.ui.list.ListViewModel
import com.example.koinmvvm.ui.main.MainViewModel
import org.koin.dsl.module


/**
 * Created by Norhan Elsawi on 23/01/2020.
 */
val viewModelModule = module {

    factory { MainViewModel(get()) }
    factory { ListViewModel(get()) }
}