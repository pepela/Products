package com.peranidze.products.presentation.viewModel.productDetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.peranidze.products.domain.Repository
import com.peranidze.products.domain.model.Product
import com.peranidze.products.domain.model.SalePrice
import com.peranidze.products.extension.toFullUrl
import com.peranidze.products.presentation.viewModel.productDetail.ProductsDetailViewModel.Companion.ARG_CATEGORY_ID
import com.peranidze.products.presentation.viewModel.productDetail.ProductsDetailViewModel.Companion.ARG_PRODUCT_ID
import com.peranidze.products.rule.RxSchedulerRule
import io.reactivex.Flowable
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ProductsDetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    private val repository: Repository = mock()

    private val savedStateHandle = SavedStateHandle(ARGS_MAPS)

    private lateinit var productsDetailViewModel: ProductsDetailViewModel

    @Test
    fun init_calls_repository_with_success_and_shows_details() {
        whenever(repository.getProduct(PRODUCT_ID, CATEGORY_ID)).thenReturn(Flowable.just(PRODUCT))
        productsDetailViewModel = ProductsDetailViewModel(repository, savedStateHandle)

        verify(repository).getProduct(PRODUCT_ID, CATEGORY_ID)

        productsDetailViewModel.state.value?.let { state ->
            assertTrue(!state.isLoading)
            assertTrue(state.name == NAME)
            assertTrue(state.description == DESCRIPTION)
            assertTrue(state.imageUrl == URL.toFullUrl())
            assertTrue(state.price == PRICE.toString())
            assertTrue(state.currency == CURRENCY)
        }
    }

    @Test
    fun init_calls_repository_with_error_and_never_shows_details() {
        whenever(repository.getProduct(PRODUCT_ID, CATEGORY_ID))
            .thenReturn(Flowable.error(RuntimeException("mock_error")))
        productsDetailViewModel = ProductsDetailViewModel(repository, savedStateHandle)

        verify(repository).getProduct(PRODUCT_ID, CATEGORY_ID)

        productsDetailViewModel.state.value?.let { state ->
            assertTrue(!state.isLoading)
            assertTrue(state.name == null)
            assertTrue(state.description == null)
            assertTrue(state.imageUrl == null)
            assertTrue(state.price == null)
            assertTrue(state.currency == null)
        }
    }

    companion object {
        private const val PRODUCT_ID = 1L
        private const val CATEGORY_ID = 2L
        private const val NAME = "mock_name"
        private const val URL = "mock_url"
        private const val DESCRIPTION = "mock_description"
        private const val PRICE = 10.50
        private const val CURRENCY = "eur"
        private val ARGS_MAPS = mapOf(
            ARG_PRODUCT_ID to PRODUCT_ID,
            ARG_CATEGORY_ID to CATEGORY_ID
        )
        private val PRODUCT =
            Product(
                PRODUCT_ID,
                CATEGORY_ID,
                NAME,
                URL,
                DESCRIPTION,
                SalePrice(PRICE, CURRENCY)
            )
    }

}
