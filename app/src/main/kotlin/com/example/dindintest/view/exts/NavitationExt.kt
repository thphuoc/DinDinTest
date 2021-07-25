package com.example.dindintest.view.exts

import android.content.Intent
import com.example.dindintest.view.base.BaseActivity
import com.example.dindintest.view.base.BaseFragment

fun BaseFragment.goBack() {
    activity?.finish()
}

fun <S : BaseActivity> BaseFragment.goNext(target: Class<S>) {
    activity?.run {
        val intent = Intent(this, target)
        startActivity(intent)
    }
}