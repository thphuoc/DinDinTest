package com.example.dindintest.view.screens.ingredient

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dindintest.R
import com.example.dindintest.data.dao.category.CategoryDAO
import com.example.dindintest.data.usecases.GetListIngredientCategoryUseCase
import com.example.dindintest.data.usecases.SearchIngredientUseCase
import com.example.dindintest.exts.observe
import com.example.dindintest.view.base.TitleBarSearchFragment
import com.example.dindintest.view.screens.ingredient.viewBinder.IngredientItemViewBinder
import com.example.dindintest.view.screens.ingredient.viewBinder.IngredientTabItemViewBinder
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.fragment_ingredient.*
import kotlinx.android.synthetic.main.view_header_search.*
import org.koin.android.ext.android.inject

class IngredientFragment : TitleBarSearchFragment() {
    override val layoutResId: Int = R.layout.fragment_ingredient
    private val getCategoryUseCase : GetListIngredientCategoryUseCase by inject()
    private val searchIngredientUseCase : SearchIngredientUseCase by inject()

    private lateinit var currentCategories: List<CategoryDAO>
    private val onCategorySelectedSubject = BehaviorSubject.create<CategoryDAO>()
    private var currentCategorySelected: CategoryDAO? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fitStatusBar(imgBack)
        tabPlaceHolder.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        placeHolderView.layoutManager = GridLayoutManager(requireContext(), 2)
        loadCategory()
    }

    private fun loadCategory() {
        stateLayout.showProgress()
        getCategoryUseCase.execute().observe(this, false, onSuccess = {
            currentCategories = it
            val listTabViewItems = currentCategories.map { category ->
                IngredientTabItemViewBinder(this, category, onCategorySelectedSubject)
            }
            listTabViewItems.forEach { tabViewItem ->
                tabPlaceHolder.addView(tabViewItem)
            }
            currentCategories.firstOrNull()?.let { firstCategoryToFocus ->
                currentCategorySelected = firstCategoryToFocus
                onCategorySelectedSubject.onNext(firstCategoryToFocus)
            }

            onCategorySelectedSubject.observe(this, false, onNext = { categorySelected ->
                currentCategorySelected = categorySelected
                reload()
            })
        }, onError = {
            stateLayout.showError(message = it.message ?: "", onClickStateButton = {
                loadCategory()
            })
        })
    }

    override fun loadData(
        pageIndex: Int,
        onResult: (viewBinders: List<Any>, hasMore: Boolean) -> Unit
    ) {
        searchIngredientUseCase.execute(
            categoryId = currentCategorySelected?.id ?: 0,
            edtSearch.text.toString()
        ).observe(this, false, onSuccess = {
            val listIngredientToDisplay = it.content?.map { ingredientItem ->
                IngredientItemViewBinder(
                    ingredient = ingredientItem
                )
            } ?: listOf()

            onResult(listIngredientToDisplay, it.hasMore())
        })
    }
}