package com.example.dindintest.view.screens.ingredient.viewBinder

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.dindintest.R
import com.example.dindintest.data.dao.ingredient.IngredientItemDAO
import com.example.dindintest.view.exts.loadImage
import com.github.florent37.shapeofview.shapes.RoundRectView
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View

@Layout(R.layout.view_ingredient_item)
class IngredientItemViewBinder(private val ingredient: IngredientItemDAO) {

    @View(R.id.imgFood)
    lateinit var imgFood: ImageView

    @View(R.id.tvName)
    lateinit var tvName: TextView

    @View(R.id.tvNoOfAvailable)
    lateinit var tvNoOfAvailable: TextView

    @View(R.id.shapeView)
    lateinit var shapeView: RoundRectView

    @View(R.id.tvLabel)
    lateinit var tvLabel: TextView

    @Resolve
    fun onResolve() {
        imgFood.loadImage(ingredient.image ?: "")
        tvName.text = ingredient.name
        tvNoOfAvailable.text = "${ingredient.noOfAvailable ?: 0}"
        if (ingredient.isAvailable == true) {
            tvLabel.setBackgroundColor(ContextCompat.getColor(shapeView.context, R.color.red))
            shapeView.setBorderColor(ContextCompat.getColor(shapeView.context, R.color.red))
        } else {
            tvLabel.setBackgroundColor(ContextCompat.getColor(shapeView.context, R.color.grayLight))
            shapeView.setBorderColor(ContextCompat.getColor(shapeView.context, R.color.grayLight))
        }
    }
}