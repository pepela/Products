package com.peranidze.products.presentation.viewModel.productsList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.FragmentNavigator
import com.nhaarman.mockito_kotlin.*
import com.peranidze.products.app.productsList.adapter.ItemRow
import com.peranidze.products.domain.Repository
import com.peranidze.products.domain.model.Category
import com.peranidze.products.presentation.mapper.ItemRowMapper
import com.peranidze.products.presentation.route.ProductListToProductDetailsRoute
import com.peranidze.products.rule.RxSchedulerRule
import io.reactivex.Flowable
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ProductsListViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    private val repository: Repository = mock()

    private val itemRowMapper: ItemRowMapper = mock()

    private val savedStateHandle = SavedStateHandle()

    private lateinit var productsListViewModel: ProductsListViewModel

    @Test
    fun init_calls_repository() {
        createViewModelAndStubWith(SUCCESS_RESULT)
        verify(repository).getCategories()
    }

    @Test
    fun init_with_success_shows_no_loading_and_no_error_and_row_items() {
        createViewModelAndStubWith(SUCCESS_RESULT)

        assertTrue(savedStateHandle.contains(ProductsListViewModel.ARG_RESPONSE))
        productsListViewModel.state.value?.let { state ->
            assertTrue(!state.isLoading)
            assertTrue(!state.isError)
            assertTrue(state.listItems.size == 2)
        }
    }

    @Test
    fun init_with_error_shows_no_loading_and_error_and_no_row_items() {
        createViewModelAndStubWith(ERROR_RESULT)

        assertFalse(savedStateHandle.contains(ProductsListViewModel.ARG_RESPONSE))
        productsListViewModel.state.value?.let { state ->
            assertTrue(!state.isLoading)
            assertTrue(state.isError)
            assertTrue(state.listItems.isEmpty())
        }
    }

    @Test
    fun init_uses_data_from_saved_state() {
        val savedStateHandleWithData =
            SavedStateHandle(mapOf(ProductsListViewModel.ARG_RESPONSE to listOf(PRODUCT_ITEM_ROW)))
        productsListViewModel =
            ProductsListViewModel(repository, itemRowMapper, savedStateHandleWithData)
        verify(repository, never()).getCategories()

        productsListViewModel.state.value?.let { state ->
            assertTrue(!state.isLoading)
            assertTrue(!state.isError)
            assertTrue(state.listItems.first() == PRODUCT_ITEM_ROW)
        }
    }

    @Test
    fun onProductItemClicked_emits_navigation_event() {
        createViewModelAndStubWith(SUCCESS_RESULT)
        val fragmentNavigatorExtras = FragmentNavigator.Extras.Builder().build()
        val route = ProductListToProductDetailsRoute(
            PRODUCT_ID,
            CATEGORY_ID,
            PRODUCT_ITEM_ROW.getSharedElementId(),
            fragmentNavigatorExtras
        )
        productsListViewModel.onProductItemClicked(PRODUCT_ITEM_ROW, fragmentNavigatorExtras)

        assertTrue(productsListViewModel.navigationToDetailEvent.value?.peekContent() == route)
    }

    @Test
    fun onRetryClicked_calls_repository() {
        createViewModelAndStubWith(ERROR_RESULT)
        productsListViewModel.onRetryClicked()

        verify(repository, times(2)).getCategories()
    }

    private fun createViewModelAndStubWith(flowable: Flowable<List<Category>>) {
        whenever(repository.getCategories()).thenReturn(flowable)
        productsListViewModel = ProductsListViewModel(repository, itemRowMapper, savedStateHandle)
    }

    companion object {
        private const val PRODUCT_ID = 1L
        private const val CATEGORY_ID = 2L
        private val PRODUCT_ITEM_ROW =
            ItemRow.ProductItem(PRODUCT_ID, CATEGORY_ID, "", "", "", "", "")
        private val CATEGORY_1 = Category("Food", emptyList())
        private val CATEGORY_2 = Category("Drinks", emptyList())
        private val CATEGORIES_LIST = listOf(CATEGORY_1, CATEGORY_2)
        private val SUCCESS_RESULT = Flowable.just(CATEGORIES_LIST)
        private val ERROR_RESULT: Flowable<List<Category>> =
            Flowable.error(RuntimeException("mock error"))
    }
}
