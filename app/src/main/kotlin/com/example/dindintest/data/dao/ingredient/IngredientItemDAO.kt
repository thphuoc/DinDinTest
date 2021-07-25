package com.example.dindintest.data.dao.ingredient

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class IngredientItemDAO(
    @SerializedName("image") val image: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("noOfAvailable") val noOfAvailable: Int? = null,
    @SerializedName("isAvailable") val isAvailable: Boolean? = null
)