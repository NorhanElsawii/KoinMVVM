package com.example.koinmvvm.di

import com.example.koinmvvm.ui.list.ListRepository
import com.example.koinmvvm.ui.main.MainRepository
import org.koin.dsl.module

/**
 * Created by Norhan Elsawi on 23/01/2020.
 */
val repositoryModule = module {

    single { MainRepository(get(), get()) }
    single { ListRepository(get()) }
}