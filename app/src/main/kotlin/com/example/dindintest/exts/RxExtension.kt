package com.example.dindintest.exts

import com.example.dindintest.data.apimgr.exceptions.InvalidAccessTokenException
import com.example.dindintest.data.context.IViewContext
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <S> Single<S>.observe(
    context: IViewContext,
    showLoading: Boolean = true,
    onSuccess: (it: S) -> Unit = {},
    onError: (throwable: Throwable) -> Unit = { throwable -> handleError(context, throwable) }
): Disposable {
    if (showLoading) {
        context.showLoadingDialog()
    }
    val disposable = this.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread()).subscribe({
            if (showLoading) {
                context.hideLoadingDialog()
            }
            onSuccess(it)
        }, {
            if (showLoading) {
                context.hideLoadingDialog()
            }
            onError(it)
        })
    context.addDisposable(disposable)

    return disposable
}

fun <S> Flowable<S>.observe(
    vm: IViewContext,
    onNext: (it: S) -> Unit = {},
    onCompleted: () -> Unit = {},
    onError: (throwable: Throwable) -> Unit = { throwable -> handleError(vm, throwable) }
) {
    vm.addDisposable(
        this.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError, onCompleted)
    )
}

fun <S> Observable<S>.observe(
    context: IViewContext,
    showLoading: Boolean = true,
    onNext: (it: S) -> Unit = {},
    onError: (throwable: Throwable) -> Unit = { throwable -> handleError(context, throwable) }
): Disposable {
    if (showLoading) {
        context.showLoadingDialog()
    }
    val disposable = this.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread()).subscribe({
            if (showLoading) {
                context.hideLoadingDialog()
            }
            onNext(it)
        }, {
            if (showLoading) {
                context.hideLoadingDialog()
            }
            onError(it)
        })
    context.addDisposable(disposable)
    return disposable
}


private fun handleError(context: IViewContext, throwable: Throwable) {
    var showError = true
    throwable.printStackTrace()
    if (throwable is InvalidAccessTokenException) {
        showError = false
    }
    throwable.cause?.let { errorCause ->
        if (errorCause is InvalidAccessTokenException) {
            showError = false
        }
    }
    if (showError) {
        context.showErrorDialog(
            message = throwable.message ?: "",
            onYes = {},
            onDismiss = {
            }
        )
    }
}

fun Completable.observe(
    context: IViewContext,
    showLoading: Boolean = false,
    onCompleted: () -> Unit = {},
    onError: (throwable: Throwable) -> Unit = { throwable -> handleError(context, throwable) }
) {
    if (showLoading) {
        context.showLoadingDialog()
    }
    context.addDisposable(
        this.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (showLoading) {
                    context.hideLoadingDialog()
                }
                onCompleted()
            }, {
                if (showLoading) {
                    context.hideLoadingDialog()
                }
                onError(it)
            })
    )
}