package com.example.dindintest.data.usecases

import com.example.dindintest.data.dao.category.CategoryDAO
import com.example.dindintest.data.helper.transformResponse
import com.example.dindintest.data.services.IGetListIngredientCategoryService
import io.reactivex.Single
import kotlin.random.Random

class GetListIngredientCategoryUseCase(private val service: IGetListIngredientCategoryService) {

    fun execute() : Single<List<CategoryDAO>> {
        return service.getData(if(Random.nextBoolean()) 1 else 2).transformResponse { it }
    }
}