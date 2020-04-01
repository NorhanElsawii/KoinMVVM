package com.example.koinmvvm.data.remote.api

import com.example.koinmvvm.data.remote.ApiUrls
import com.example.koinmvvm.data.remote.BaseResponse
import com.example.koinmvvm.data.remote.entities.ResultsItem
import com.example.koinmvvm.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET(ApiUrls.GET_LIST)
    suspend fun executeGetMovieList(@Query(PAGE) page: Int, @Query(API_KEY) apiKey: String = Constants.X_API_KEY): Response<BaseResponse<List<ResultsItem>>>

    companion object {
        const val PAGE = "page"
        const val API_KEY = "api_key"
    }
}