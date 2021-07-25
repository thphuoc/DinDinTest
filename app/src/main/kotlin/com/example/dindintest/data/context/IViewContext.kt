package com.example.dindintest.data.context

import com.afollestad.materialdialogs.MaterialDialog
import io.reactivex.disposables.Disposable
import java.io.File

interface IViewContext {
    fun addDisposable(disposable: Disposable)
    fun showLoadingDialog()
    fun hideLoadingDialog()
    fun showErrorDialog(
        message: String,
        cancelable: Boolean = true,
        onYes: () -> Unit = {},
        onDismiss: () -> Unit = {}
    ): MaterialDialog?

    fun getDataDir(): File

    fun onBackPressed()
}