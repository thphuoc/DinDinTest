package com.example.dindintest.view.screens.orders

import android.os.Bundle
import android.view.View
import butterknife.OnClick
import com.example.dindintest.R
import com.example.dindintest.data.usecases.GetListOrderUseCase
import com.example.dindintest.exts.observe
import com.example.dindintest.view.base.TitleBarFragment
import com.example.dindintest.view.exts.goNext
import com.example.dindintest.view.exts.now
import com.example.dindintest.view.screens.ingredient.IngredientActivity
import com.example.dindintest.view.screens.orders.viewBinder.ViewOrderItemViewBinder
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.fragment_orders.*
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class OrderFragment : TitleBarFragment() {
    override val layoutResId: Int = R.layout.fragment_orders
    override fun getTitle(): String = getString(R.string.lb_page_orders_title)
    private val getOrderUseCase : GetListOrderUseCase by inject()
    private val secondIntervalSubject = BehaviorSubject.createDefault(now())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Observable.interval(1, TimeUnit.SECONDS).observe(this, false, onNext = {
            secondIntervalSubject.onNext(now())
        })
    }

    override fun loadData(
        pageIndex: Int,
        onResult: (viewBinders: List<Any>, hasMore: Boolean) -> Unit
    ) {
        getOrderUseCase.execute().observe(this, false, onSuccess = { orders ->
            val listOrderItemToDisplay = orders.map { order ->
                ViewOrderItemViewBinder(
                    fragment = this,
                    order = order,
                    secondInternalSubject = secondIntervalSubject,
                    removeItem = { orderViewItemToRemove ->
                        placeHolderView.removeView(orderViewItemToRemove)
                        if (placeHolderView.allViewResolvers.isEmpty()) {
                            stateLayout.showEmpty()
                        }
                    }
                )
            }
            onResult(listOrderItemToDisplay, false)
        }, onError = {
            stateLayout.showError(message = it.message ?: "", onClickStateButton = { reload() })
        })
    }

    @OnClick(R.id.imgIngredient)
    fun onClickIngredient() {
        goNext(IngredientActivity::class.java)
    }
}