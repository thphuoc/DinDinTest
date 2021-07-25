package com.example.dindintest.view.exts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.dindintest.R
import cz.kinst.jakub.view.SimpleStatefulLayout

fun SimpleStatefulLayout.buildStateEmptyLayout(
    layoutResId: Int = R.layout.view_state_empty, customView: (view: View) -> Unit = {},
    iconResId: Int = -1,
    textResId: Int = -1
) {
    emptyView =
        LayoutInflater.from(context).inflate(layoutResId, null, false).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            if (iconResId > 0) {
                this.findViewById<ImageView>(R.id.imgEmpty)?.setImageResource(iconResId)
            }
            if (textResId > 0) {
                this.findViewById<TextView>(R.id.tvNoResultSearch)?.setText(textResId)
            }
            customView(this)
        }
}

fun SimpleStatefulLayout.buildStateInitLayout(
    layoutResId: Int = R.layout.view_state_init, customView: (view: View) -> Unit = {}
) {
    offlineView =
        LayoutInflater.from(context).inflate(layoutResId, null, false).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            customView(this)
        }
}

fun SimpleStatefulLayout.buildStateLoadingLayout(
    layoutResId: Int = R.layout.view_state_loading, customView: (view: View) -> Unit = {}
) {
    progressView =
        LayoutInflater.from(context).inflate(layoutResId, null, false).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            customView(this)
        }
}