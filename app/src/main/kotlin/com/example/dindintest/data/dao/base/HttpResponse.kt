package com.example.dindintest.data.dao.base

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
open class HttpResponse<E>(default: E? = null, defaultStatus: Int = 200) {
    @SerializedName("status")
    var status: Int? = defaultStatus

    @SerializedName("message")
    val message: String? = null

    @SerializedName("data")
    val data: E? = default

    fun isUnauthorized(): Boolean = status == STATUS_CODE_UNAUTHORIZED
    fun isSuccess(): Boolean = status  == STATUS_CODE_SUCCEED

    companion object {
        private const val STATUS_CODE_SUCCEED = 200
        private const val STATUS_CODE_UNAUTHORIZED = 401
    }
}