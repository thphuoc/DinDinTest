package com.example.dindintest.injection

import com.example.dindintest.data.services.IGetListIngredientCategoryService
import com.example.dindintest.data.services.IGetListOrderService
import com.example.dindintest.data.services.ISearchIngredientService
import com.example.dindintest.data.services.mockService
import com.example.dindintest.data.usecases.GetListIngredientCategoryUseCase
import com.example.dindintest.data.usecases.GetListOrderUseCase
import com.example.dindintest.data.usecases.SearchIngredientUseCase
import org.koin.dsl.module

val serviceModule = module {
    factory { mockService.build(IGetListIngredientCategoryService::class.java) }
    factory { mockService.build(IGetListOrderService::class.java) }
    factory { mockService.build(ISearchIngredientService::class.java) }
}

val usecaseModule = module {
    factory { GetListIngredientCategoryUseCase(get()) }
    factory { GetListOrderUseCase(get()) }
    factory { SearchIngredientUseCase(get()) }
}