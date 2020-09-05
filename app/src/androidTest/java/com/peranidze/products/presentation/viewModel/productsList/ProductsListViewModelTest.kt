package com.peranidze.products.presentation.viewModel.productsList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.FragmentNavigator
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.peranidze.products.app.productsList.adapter.ItemRow
import com.peranidze.products.domain.Repository
import com.peranidze.products.domain.model.Category
import com.peranidze.products.presentation.mapper.ItemRowMapper
import com.peranidze.products.presentation.route.ProductListToProductDetailsRoute
import io.reactivex.Flowable
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ProductsListViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository: Repository = mock()

    private val itemRowMapper: ItemRowMapper = mock()

    private val savedStateHandle = SavedStateHandle()

    private lateinit var productsListViewModel: ProductsListViewModel

    @Test
    fun initCallsRepository() {
        createViewModelAndStubWith(SUCCESS_RESULT)
        verify(repository).getCategories()
    }

    @Test
    fun initWithSuccessShowsNoLoadingAndNoErrorAndRowItems() {
        createViewModelAndStubWith(SUCCESS_RESULT)
        verify(repository).getCategories()

        productsListViewModel.state.value?.let { state ->
            assertTrue(!state.isLoading)
            assertTrue(!state.isError)
            assertTrue(state.listItems.size == 2)
        }
    }

    @Test
    fun initWithErrorShowsNoLoadingAndErrorAndNoRowItems() {
        createViewModelAndStubWith(ERROR_RESULT)
        verify(repository).getCategories()

        productsListViewModel.state.value?.let { state ->
            assertTrue(!state.isLoading)
            assertTrue(state.isError)
            assertTrue(state.listItems.isEmpty())
        }
    }

    @Test
    fun onProductItemClicked() {
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
