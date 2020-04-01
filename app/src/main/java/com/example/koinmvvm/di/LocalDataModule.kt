package com.example.koinmvvm.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.koinmvvm.data.local.ConnectivityUtils
import com.example.koinmvvm.data.local.LocalDataUtils
import com.example.koinmvvm.data.local.database.DatabaseFile
import com.example.koinmvvm.data.local.database.dao.MovieDao
import com.example.koinmvvm.utils.Constants
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Created by Norhan Elsawi on 23/01/2020.
 */
val localDataModule = module {

    fun provideConnectivityUtils(context: Context): ConnectivityUtils {
        return ConnectivityUtils(context)
    }

    fun provideLocalDataUtils(context: Context): LocalDataUtils {
        return LocalDataUtils(context)
    }

    fun provideDatabase(application: Application): DatabaseFile {
        return Room.databaseBuilder(application, DatabaseFile::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    single { provideConnectivityUtils(androidApplication()) }
    single { provideLocalDataUtils(androidApplication()) }
    single { provideDatabase(androidApplication()) }


    //<----------------------------------------------------------------------------------------------------------->//

    fun provideDao(database: DatabaseFile): MovieDao {
        return database.movieDao
    }
    single { provideDao(get()) }

}