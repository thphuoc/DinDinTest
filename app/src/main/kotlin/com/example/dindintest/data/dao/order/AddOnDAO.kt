package com.example.dindintest.data.dao.order

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AddOnDAO(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("quantity") val quantity: Int? = null,
)
