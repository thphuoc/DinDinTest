package com.example.dindintest.data.dao.order

import androidx.annotation.Keep
import com.example.dindintest.view.exts.formatHHMM
import com.example.dindintest.view.exts.now
import com.example.dindintest.view.exts.parseToMilis
import com.google.gson.annotations.SerializedName

@Keep
data class OrderItemDAO(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("quantity") val quantity: Int? = null,
    @SerializedName("created_at") var created_at: String? = null,
    @SerializedName("alerted_at") var alerted_at: String? = null,
    @SerializedName("expired_at") var expired_at: String? = null,
    @SerializedName("addon") val addon: List<AddOnDAO>? = null
) {
    var hasExpired = false

    fun getCreatedTimeToDisplay(): String {
        return created_at?.formatHHMM() ?: "--:--"
    }

    fun getCreatedTimeInMs(): Long {
        return created_at?.parseToMilis() ?: now()
    }

    fun getExpireTimeInMs(): Long {
        return expired_at?.parseToMilis() ?: now()
    }

    fun getAlertedTimeInMs(): Long {
        return alerted_at?.parseToMilis() ?: now()
    }
}