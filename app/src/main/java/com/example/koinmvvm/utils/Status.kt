package com.example.koinmvvm.utils

/**
 * Created by Norhan Elsawi on 23/01/2020.
 */
sealed class Status {
    data class Success<T>(
        var data: T?
    ) : Status()

    object SuccessLoadingMore : Status()

    data class Error(
        var code: Int = 0,
        var message: String? = ""
    ) : Status()

    data class ErrorLoadingMore(var code: Int = 0, var message: String?) : Status()

    object Loading : Status()

    object LoadingMore : Status()
}