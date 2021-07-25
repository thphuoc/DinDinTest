package com.example.dindintest.data.services

import com.example.dindintest.data.dao.base.HttpResponse
import com.example.dindintest.data.dao.category.CategoryDAO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface IGetListIngredientCategoryService {
    @GET("/category/{randomListIndex}/list")
    fun getData(@Path("randomListIndex") randomListIndex: Int): Single<HttpResponse<List<CategoryDAO>>>
}