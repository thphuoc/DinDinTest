package com.example.dindintest.data.services

import com.example.dindintest.data.dao.base.HttpResponse
import com.example.dindintest.data.dao.order.OrderItemDAO
import io.reactivex.Single
import retrofit2.http.GET

interface IGetListOrderService {
    @GET("/orders")
    fun getData(): Single<HttpResponse<List<OrderItemDAO>>>
}