package com.example.dindintest.data.dao.category

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CategoryDAO(
    @SerializedName("name") val categoryName: String? = null,
    @SerializedName("id") val id: Int? = null,
)
