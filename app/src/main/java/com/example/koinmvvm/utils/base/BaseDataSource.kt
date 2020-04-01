package com.example.koinmvvm.utils.base

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.koinmvvm.R
import com.example.koinmvvm.data.remote.BaseResponse
import com.example.koinmvvm.utils.Status
import com.example.koinmvvm.utils.extensions.toObjectFromJson
import io.reactivex.functions.Action
import kotlinx.coroutines.*
import retrofit2.Response
import kotlin.coroutines.CoroutineContext


/**
 * Created by Norhan Elsawi on 23/01/2020.
 */
abstract class BaseDataSource<I>(
    private val repository: BaseRepository,
    private val status: MutableLiveData<Status>
) :
    PageKeyedDataSource<Int, I>(), CoroutineScope {

    private var retry: Action? = null
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    fun <D> performNetworkCall(
        apiCall: suspend () -> Response<BaseResponse<D>>,
        retryAction: Action?,
        callBack: (m: D?) -> Unit,
        isLoadMore: Boolean,
        doOnSuccess: (responseData: D?) -> Unit = {}
    ) {
        setRetry(retryAction)
        when {
            repository.isNetworkConnected() -> launch(coroutineContext) {
                withContext(Dispatchers.IO) {
                    try {
                        if (isLoadMore)
                            status.postValue(Status.LoadingMore)
                        else
                            status.postValue(Status.Loading)

                        val response = apiCall.invoke()

                        if (response.code() in 200..300) {
                            doOnSuccess(response.body()?.data)
                            if (isLoadMore)
                                status.postValue(Status.SuccessLoadingMore)
                            else
                                status.postValue(Status.Success(response.body()?.data))

                            callBack(response.body()?.data)
                            setRetry(null)

                        } else {
                            val error =
                                response.errorBody()?.string()
                                    .toObjectFromJson<BaseResponse<D>>(BaseResponse::class.java)
                            if (isLoadMore)
                                status.postValue(
                                    Status.ErrorLoadingMore(
                                        response.code(),
                                        message = if (error.message.isNullOrEmpty()) repository.getString(
                                            R.string.some_thing_went_wrong_error_msg
                                        ) else error.message
                                    )
                                )
                            else
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
                        if (isLoadMore)
                            status.postValue(
                                Status.ErrorLoadingMore(
                                    message = repository.getString(R.string.some_thing_went_wrong_error_msg)
                                )
                            )
                        else
                            status.postValue(
                                Status.Error(
                                    message = repository.getString(R.string.some_thing_went_wrong_error_msg)
                                )
                            )
                    }
                }
            }
            isLoadMore -> status.postValue(Status.ErrorLoadingMore(message = repository.getString(R.string.check_internet_connection)))
            else -> status.postValue(Status.Error(message = repository.getString(R.string.check_internet_connection)))
        }
    }

    fun retry() {
        retry?.run()
    }

    private fun setRetry(action: Action?) {
        retry = action
    }

    override fun invalidate() {
        job.cancel()
        super.invalidate()
    }

    fun onCleared() {
        job.cancel()
    }
}