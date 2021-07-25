package com.example.dindintest.view.screens.ingredient.viewBinder

import android.widget.TextView
import com.example.dindintest.R
import com.example.dindintest.data.dao.category.CategoryDAO
import com.example.dindintest.exts.observe
import com.example.dindintest.view.base.BaseFragment
import com.example.dindintest.view.exts.showIf
import com.mindorks.placeholderview.annotations.Click
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import io.reactivex.subjects.BehaviorSubject

@Layout(R.layout.view_tab_item)
class IngredientTabItemViewBinder(
    private val fragment: BaseFragment,
    private val category: CategoryDAO,
    private val tabSelectSubject: BehaviorSubject<CategoryDAO>
) {

    @View(R.id.viewIndicator)
    lateinit var viewIndicator: android.view.View

    @View(R.id.tvTabText)
    lateinit var tvTabText: TextView

    @Resolve
    fun onResolve() {
        tvTabText.text = category.categoryName
        tabSelectSubject.observe(fragment, false, onNext = { categorySelected ->
            val isShowIndicator = category.id == categorySelected.id
            viewIndicator.showIf(isShowIndicator, !isShowIndicator)
        })
    }

    @Click(R.id.rlTabItem)
    fun onClickItem() {
        tabSelectSubject.onNext(category)
    }
}