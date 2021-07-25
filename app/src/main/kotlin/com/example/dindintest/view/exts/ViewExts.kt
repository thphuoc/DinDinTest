package com.example.dindintest.view.exts

import android.view.View

fun View.showIf(showIf: Boolean, goneIf: Boolean) {
    this.visibility = when {
        showIf -> View.VISIBLE
        goneIf -> View.GONE
        else -> View.INVISIBLE
    }
}