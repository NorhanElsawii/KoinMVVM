package com.example.koinmvvm.di

import android.content.Context
import com.example.koinmvvm.BuildConfig
import com.example.koinmvvm.BuildConfig.MAIN_HOST
import com.example.koinmvvm.data.local.sharedpreference.SharedPreferencesUtils
import com.example.koinmvvm.data.remote.api.MovieApi
import com.example.koinmvvm.data.remote.apicalls.MovieApiCalls
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by Norhan Elsawi on 23/01/2020.
 */
val networkModule = module {

    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MAIN_HOST)
            .client(okHttpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        context: Context
    ): OkHttpClient {
        val sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context)
        val builder = OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor {
                val request = it.request().newBuilder()
//                    .addHeader("x-api-key", "d123")
//                if (sharedPreferencesUtils.isLoggedInUser())
//                    request.addHeader(
//                        "authorization"
//                        , "Bearer ${sharedPreferencesUtils.getUser()?.token}"
//                    )
//                request.addHeader("Accept-Language", sharedPreferencesUtils.getLanguage())
//                request.addHeader("Accept", "application/json")
                it.proceed(request.build())
            }
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
        return builder
            .build()
    }

    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return logging
    }

    single { provideOkHttpClient(get(), get()) }
    single { provideLoggingInterceptor() }
    single { provideRetrofit(get()) }

    //<----------------------------------------------------------------------------------------------------------->//

    fun provideUserApiCalls(retrofit: Retrofit): MovieApiCalls {
        return MovieApiCalls(retrofit.create(MovieApi::class.java))
    }

    single { provideUserApiCalls(get()) }
}