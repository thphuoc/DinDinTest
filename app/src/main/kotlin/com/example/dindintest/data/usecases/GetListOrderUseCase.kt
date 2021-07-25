package com.example.dindintest.data.usecases

import com.example.dindintest.data.dao.order.OrderItemDAO
import com.example.dindintest.data.helper.transformResponse
import com.example.dindintest.data.services.IGetListOrderService
import com.example.dindintest.view.exts.now
import com.example.dindintest.view.exts.toTimeString
import io.reactivex.Single

class GetListOrderUseCase(private val service: IGetListOrderService) {

    fun execute(): Single<List<OrderItemDAO>> {
        return service.getData().transformResponse { orders ->
            //I will update fake time to Demo
            orders.forEach {
                it.created_at = now().toTimeString()
                it.alerted_at = (now() + 5000L).toTimeString()
                it.expired_at = (now() + 10000L).toTimeString()
            }

            orders
        }
    }
}