package com.example.dindintest.data.usecases

import com.example.dindintest.data.dao.base.HttpResponse
import com.example.dindintest.data.dao.base.PagingDataDAO
import com.example.dindintest.data.dao.ingredient.IngredientItemDAO
import com.example.dindintest.data.services.ISearchIngredientService
import io.reactivex.Single
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchIngredientUseCaseTest : TestCase() {

    @Mock
    lateinit var service: ISearchIngredientService

    @Test
    fun testSearchIngredientCaseSuccess() {
        val paging = PagingDataDAO(
            content = listOf(
                IngredientItemDAO(
                    image = "image1",
                    name = "name1",
                    noOfAvailable = 1,
                    isAvailable = true
                )
            ),
            last = true
        )

        val response = HttpResponse(default = paging, defaultStatus = 200)
        Mockito.`when`(service.getData(1, ""))
            .thenReturn(Single.just(response))

        val getCategoryUseCase = SearchIngredientUseCase(service)

        getCategoryUseCase.execute(1, "").subscribe({ output ->
            assert(output.last == true)
            assertEquals(paging.content?.size, output.content?.size)
            paging.content?.forEachIndexed { index, inItem ->
                val out = output.content?.getOrNull(index)
                assertEquals(inItem.image, out?.image)
                assertEquals(inItem.name, out?.name)
                assertEquals(inItem.noOfAvailable, out?.noOfAvailable)
                assertEquals(inItem.isAvailable, out?.isAvailable)
            }
        }, {})
    }
}