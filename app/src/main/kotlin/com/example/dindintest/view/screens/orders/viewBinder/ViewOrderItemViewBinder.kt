package com.example.dindintest.view.screens.orders.viewBinder

import android.content.res.ColorStateList
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import com.example.dindintest.R
import com.example.dindintest.data.dao.order.OrderItemDAO
import com.example.dindintest.exts.observe
import com.example.dindintest.view.base.BaseFragment
import com.example.dindintest.view.exts.convertMsToTimeLeft
import com.example.dindintest.view.exts.now
import com.mindorks.placeholderview.PlaceHolderView
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import io.reactivex.subjects.BehaviorSubject

@Layout(R.layout.view_order_item)
class ViewOrderItemViewBinder(
    private val fragment: BaseFragment,
    private val order: OrderItemDAO,
    private val secondInternalSubject: BehaviorSubject<Long>,
    private val removeItem: (orderViewItem: ViewOrderItemViewBinder) -> Unit
) {

    @View(R.id.tvOrderNumber)
    lateinit var tvOrderNumber: TextView

    @View(R.id.tvTime)
    lateinit var tvTime: TextView

    @View(R.id.tvTimeLeft)
    lateinit var tvTimeLeft: TextView

    @View(R.id.progressTimeLeft)
    lateinit var progressTimeLeft: ProgressBar

    @View(R.id.addOnPlaceHolder)
    lateinit var addOnPlaceHolder: PlaceHolderView

    @View(R.id.btnAccept)
    lateinit var btnAccept: Button

    @Resolve
    fun onResolve() {
        tvOrderNumber.text = "#${order.id}"
        tvTime.text = order.getCreatedTimeToDisplay()
        tvTimeLeft.text = "--"
        addOnPlaceHolder.removeAllViews()
        order.addon?.forEach { addOn ->
            addOnPlaceHolder.addView(ViewAddonItemViewBinder(order, addOn))
        }

        displayOrderInfo(now())
        secondInternalSubject.observe(fragment, false, onNext = { currentTime ->
            displayOrderInfo(currentTime)
        })
    }

    private fun displayOrderInfo(currentTime: Long) {
        val timeExpiredInMs = order.getExpireTimeInMs()
        val timeCreatedInMs = order.getCreatedTimeInMs()
        val timeLength = timeExpiredInMs - timeCreatedInMs

        val timeLeftInMs = order.getExpireTimeInMs() - currentTime
        val timeLeftInSecond = timeLeftInMs / 1000
        val timeLeftToDisplay = timeLeftInMs.convertMsToTimeLeft()

        progressTimeLeft.max = timeLength.toInt() / 1000
        progressTimeLeft.progress = timeLeftInSecond.toInt()

        order.hasExpired = timeLeftInSecond <= 0

        if (order.hasExpired) {
            val colorExpired = getColor(fragment.requireContext(), R.color.red)
            btnAccept.backgroundTintList = ColorStateList.valueOf(colorExpired)
            btnAccept.setOnClickListener {
                onClickExpired()
            }
            btnAccept.setText(R.string.btn_expired)
            tvTimeLeft.text = "00:00"
        } else {
            tvTimeLeft.text = timeLeftToDisplay
            val colorActive = getColor(fragment.requireContext(), R.color.colorSecondary)
            btnAccept.backgroundTintList = ColorStateList.valueOf(colorActive)
            btnAccept.setOnClickListener {
                onClickExpired()
            }
            btnAccept.setText(R.string.btn_accept)
        }
    }

    fun onClickExpired() {
        removeItem(this)
    }

    fun onClickAccept() {
        //TODO: call API to remove and then remove item
        removeItem(this)
    }
}