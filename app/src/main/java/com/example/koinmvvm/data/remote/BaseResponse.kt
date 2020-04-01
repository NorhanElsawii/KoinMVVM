package com.example.koinmvvm.data.remote

import com.google.gson.annotations.SerializedName

open class BaseResponse<R> {

    @field:SerializedName("message")
    val message: String? = null

    //TODO only for testing (should be data from server)
    //@field:SerializedName("data")
    @field:SerializedName("results")
    var data: R? = null
}