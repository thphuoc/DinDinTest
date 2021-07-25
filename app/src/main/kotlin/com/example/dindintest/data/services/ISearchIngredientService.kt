package com.example.dindintest.data.services

import com.example.dindintest.data.dao.base.HttpResponse
import com.example.dindintest.data.dao.base.PagingDataDAO
import com.example.dindintest.data.dao.ingredient.IngredientItemDAO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ISearchIngredientService {
    @GET("/search/ingredient")
    fun getData(
        @Query("categoryId") categoryId: Int,
        @Query("query") query: String
    ): Single<HttpResponse<PagingDataDAO<IngredientItemDAO>>>
}