package com.example.dindintest.data.helper

import com.example.dindintest.data.dao.base.HttpResponse
import io.reactivex.Completable
import io.reactivex.Single

fun <I, O> Single<HttpResponse<I>>.transformResponseWithDefault(
    default: O,
    mapFunction: (response: I) -> O = { default }
): Single<O> {
    return this.map { response ->
        if (response.isSuccess()) {
            if (response.data != null) mapFunction(response.data) else default
        } else {
            throw Exception(response.message ?: "")
        }
    }
}

fun <I, O> Single<HttpResponse<I>>.transformResponse(
    mapFunction: (response: I) -> O
): Single<O> {
    return this.map { response ->
        if (response.isSuccess() && response.data != null) {
            mapFunction(response.data)
        } else {
            throw Exception(response.message ?: "")
        }
    }
}

fun <I> Single<HttpResponse<I>>.transformCompletable(onResult: (data: I) -> Unit = {}): Completable {
    return this.flatMapCompletable { response ->
        if (response.isSuccess() && response.data != null) {
            onResult(response.data)
            Completable.complete()
        } else {
            throw Exception(response.message ?: "")
        }
    }
}