package com.example.dindintest.data.usecases

import com.example.dindintest.data.apimgr.exceptions.RemoteException
import com.example.dindintest.data.dao.base.HttpResponse
import com.example.dindintest.data.dao.category.CategoryDAO
import com.example.dindintest.data.services.IGetListIngredientCategoryService
import io.reactivex.Single
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetListIngredientCategoryUseCaseTest : TestCase() {

    @Mock
    lateinit var service: IGetListIngredientCategoryService

    @Test
    fun testGetCategoryCaseSuccess() {
        val input = listOf(
            CategoryDAO(id = 1, categoryName = "Name1"),
            CategoryDAO(id = 2, categoryName = "Name2")
        )

        val response = HttpResponse(default = input, defaultStatus = 200)
        Mockito.`when`(service.getData()).thenReturn(Single.just(response))

        val getCategoryUseCase = GetListIngredientCategoryUseCase(service)

        getCategoryUseCase.execute().subscribe({ output ->
            assertEquals(input.size, output.size)
            input.forEachIndexed { index, catIn ->
                val catOut = output[index]
                assertEquals(catIn.id, catOut.id)
                assertEquals(catIn.categoryName, catOut.categoryName)
            }
        }, {})
    }
}