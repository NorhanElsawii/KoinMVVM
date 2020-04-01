package com.example.koinmvvm.utils.base

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.koinmvvm.R
import com.example.koinmvvm.utils.Status
import com.example.koinmvvm.utils.extensions.hideSoftKeyboard
import com.example.koinmvvm.utils.extensions.observe
import com.example.koinmvvm.utils.extensions.showSoftKeyboard
import com.tapadoo.alerter.Alerter

/**
 * Created by Norhan Elsawi on 23/01/2020.
 */
abstract class BaseFragment : Fragment() {

    private var pd: Dialog? = null

    abstract fun layoutId(): Int

    abstract fun getViewModel(): BaseViewModel?

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(layoutId(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        createCustomProgressDialog()
        onViewReady()
        initListeners()
    }

    private fun initListeners() {
        observe(getViewModel()?.showLoginDialog) {
            showLoginDialog()
        }

        observe(getViewModel()?.showNetworkError) {
            showErrorMsg(getString(R.string.check_internet_connection))
        }
    }

    abstract fun onViewReady()

    fun showErrorMsg(msg: String?) {
        activity?.let {
            if (!msg.isNullOrEmpty())
                Alerter.create(it)
                    .setText(msg)
                    .setTextAppearance(R.style.AlerterCustomTextAppearance)
                    .setDuration(3000)
                    .enableSwipeToDismiss()
                    .setBackgroundColorRes(R.color.red)
                    .show()
        }
    }

    fun showMsg(msg: String?) {
        activity?.let {
            if (!msg.isNullOrEmpty())
                Alerter.create(it)
                    .setText(msg)
                    .setTextAppearance(R.style.AlerterCustomTextAppearance)
                    .setDuration(3000)
                    .enableSwipeToDismiss()
                    .setBackgroundColorRes(R.color.green)
                    .show()
        }
    }

    private fun createCustomProgressDialog() {
        context?.let {
            pd = Dialog(it, R.style.DialogCustomTheme)
            pd?.setContentView(R.layout.layout_dialog_progress)
            pd?.setCancelable(false)
        }
    }

    fun showDialogLoading() {
        pd?.let {
            if (!it.isShowing)
                it.show()
        }
    }

    fun hideDialogLoading() {
        pd?.let {
            if (it.isShowing)
                it.dismiss()
        }
    }

    fun hideSoftKeyboard() {
        activity.hideSoftKeyboard()
    }

    fun showSoftKeyboard() {
        activity.showSoftKeyboard()
    }

    private fun showLoginDialog() {

    }

    fun onError(error: Status.Error, showErrorMsg: Boolean = true, handleError: () -> Unit) {
        handleErrorStatus(error.message, error.code, showErrorMsg, handleError)
    }

    fun onError(
        error: Status.ErrorLoadingMore,
        showErrorMsg: Boolean = true,
        handleError: () -> Unit
    ) {
        handleErrorStatus(error.message, error.code, showErrorMsg, handleError)
    }

    private fun handleErrorStatus(
        errorMsg: String?,
        errorCode: Int,
        showErrorMsg: Boolean = true,
        handleError: () -> Unit
    ) {
        if (showErrorMsg)
            showErrorMsg(errorMsg)
        handleError()
        handleErrorCases(errorCode)
    }

    private fun handleErrorCases(errorCode: Int) {
        if (errorCode == 401)
            Handler().postDelayed({
                logout()
            }, 2000)

    }

    fun logout() {
        getViewModel()?.logout()
        //navigate to login screen
    }

    override fun onStop() {
        super.onStop()
        Alerter.clearCurrent(activity)
    }
}