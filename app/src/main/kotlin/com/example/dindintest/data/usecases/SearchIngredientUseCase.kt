package com.example.dindintest.data.usecases

import com.example.dindintest.data.dao.base.PagingDataDAO
import com.example.dindintest.data.dao.ingredient.IngredientItemDAO
import com.example.dindintest.data.helper.transformResponse
import com.example.dindintest.data.services.ISearchIngredientService
import io.reactivex.Single

class SearchIngredientUseCase(private val service: ISearchIngredientService) {

    fun execute(categoryId: Int, contains: String): Single<PagingDataDAO<IngredientItemDAO>> {
        return service.getData(categoryId, contains).transformResponse { it }
    }
}