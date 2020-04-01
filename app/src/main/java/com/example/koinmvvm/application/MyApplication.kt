package com.example.koinmvvm.application

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.example.koinmvvm.di.localDataModule
import com.example.koinmvvm.di.networkModule
import com.example.koinmvvm.di.repositoryModule
import com.example.koinmvvm.di.viewModelModule
import com.example.koinmvvm.utils.locale.LocaleHelper
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(viewModelModule, repositoryModule, networkModule, localDataModule))
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleHelper.onAttach(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }

}