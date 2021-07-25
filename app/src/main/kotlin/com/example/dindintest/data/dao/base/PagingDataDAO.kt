package com.example.dindintest.data.dao.base

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PagingDataDAO<E>(
    @SerializedName("content")
    val content: List<E>? = listOf(),
    @SerializedName("totalPages")
    val totalPages: Long? = 0,
    @SerializedName("last")
    val last: Boolean? = false,
    @SerializedName("totalElements")
    val totalElements: Long? = 0,
    @SerializedName("numberOfElements")
    val numberOfElements: Long? = 0
) {
    fun hasMore() = last == false
}