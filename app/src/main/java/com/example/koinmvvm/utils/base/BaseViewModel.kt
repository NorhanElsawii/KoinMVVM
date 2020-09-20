package com.example.koinmvvm.utils.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koinmvvm.R
import com.example.koinmvvm.data.remote.BaseResponse
import com.example.koinmvvm.utils.SingleLiveEvent
import com.example.koinmvvm.utils.Status
import com.example.koinmvvm.utils.extensions.toObjectFromJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Created by Norhan Elsawi on 23/01/2020.
 */
abstract class BaseViewModel(private val repository: BaseRepository) : ViewModel() {

    val showLoginDialog = SingleLiveEvent<Boolean>()
    val showNetworkError = SingleLiveEvent<Boolean>()

    fun <D> performNetworkCall(
        apiCall: suspend () -> Response<BaseResponse<D>>,
        status: SingleLiveEvent<Status>,
        doOnSuccess: (responseData: D?) -> Unit = {}
    ) {
        if (repository.isNetworkConnected())
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    try {
                        status.postValue(Status.Loading)
                        val response = apiCall.invoke()
                        if (response.code() in 200..300) {
                            doOnSuccess(response.body()?.data)
                            status.postValue(Status.Success(response.body()?.data))
                        } else {
                            val error =
                                response.errorBody()?.string()
                                    .toObjectFromJson<BaseResponse<D>>(BaseResponse::class.java)
                            status.postValue(
                                Status.Error(
                                    response.code(),
                                    message = if (error.message.isNullOrEmpty()) repository.getString(
                                        R.string.some_thing_went_wrong_error_msg
                                    ) else error.message
                                )
                            )
                        }
                    } catch (e: Exception) {
                        status.postValue(
                            Status.Error(
                                message = repository.getString(R.string.some_thing_went_wrong_error_msg)
                            )
                        )
                    }
                }
            }
        else
            status.postValue(Status.Error(message = repository.getString(R.string.check_internet_connection)))
    }

    fun doInBackground(error: (e: Exception) -> Unit = {}, block: suspend () -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                try {
                    block.invoke()
                } catch (e: Exception) {
                    error.invoke(e)
                }
            }
        }
    }

    fun isUserLogin(showLoginPopUp: Boolean = true): Boolean {
        val isUserLogin = repository.isUserLogin()
        if (!isUserLogin && showLoginPopUp)
            this.showLoginDialog.postValue(true)
        return isUserLogin
    }

    fun isNetworkConnected(): Boolean {
        val isNetworkConnected = repository.isNetworkConnected()
        if (!isNetworkConnected)
            this.showNetworkError.postValue(true)
        return isNetworkConnected
    }

    fun logout() {
        repository.clearUserData()
    }
}