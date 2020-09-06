package com.peranidze.products.presentation.viewModel.productsList

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.FragmentNavigator
import com.peranidze.products.app.productsList.adapter.ItemRow
import com.peranidze.products.domain.Repository
import com.peranidze.products.domain.model.Category
import com.peranidze.products.presentation.Event
import com.peranidze.products.presentation.ViewModelAssistedFactory
import com.peranidze.products.presentation.mapper.ItemRowMapper
import com.peranidze.products.presentation.route.ProductListToProductDetailsRoute
import com.peranidze.products.presentation.viewModel.BaseViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductsListViewModel @AssistedInject constructor(
    private val repository: Repository,
    private val itemRowMapper: ItemRowMapper,
    @Assisted private val handle: SavedStateHandle
) : BaseViewModel<ProductsListViewModel.State>(State()) {

    @AssistedInject.Factory
    interface Factory : ViewModelAssistedFactory<ProductsListViewModel> {
        override fun create(handle: SavedStateHandle): ProductsListViewModel
    }

    data class State(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val listItems: List<ItemRow> = emptyList()
    )

    private val _navigationToDetailEvent =
        MutableLiveData<Event<ProductListToProductDetailsRoute>>()
    val navigationToDetailEvent: LiveData<Event<ProductListToProductDetailsRoute>>
        get() = _navigationToDetailEvent

    init {
        if (isResponseInSavedState()) {
            getResponseFromSavedState()?.let {
                showItems(it)
            }
        } else {
            fetchCategories()
        }
    }

    fun onProductItemClicked(
        productItem: ItemRow.ProductItem,
        fragmentNavigatorExtras: FragmentNavigator.Extras
    ) {
        val route = ProductListToProductDetailsRoute(
            productItem.id,
            productItem.categoryId,
            productItem.getSharedElementId(),
            fragmentNavigatorExtras
        )
        _navigationToDetailEvent.postValue(Event(route))
    }

    fun onRetryClicked() {
        fetchCategories()
    }

    private fun fetchCategories() {
        repository.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                showLoading()
                hideError()
            }
            .subscribe(::fetchCategoriesOnNext, ::fetchCategoriesOnError)
            .addToDisposables()
    }

    private fun fetchCategoriesOnNext(categoriesList: List<Category>) {
        hideLoading()
        hideError()
        with(itemRowMapper.mapToItemRowsList(categoriesList)) {
            saveResponseInSavedState(this)
            showItems(this)
        }
    }

    private fun fetchCategoriesOnError(throwable: Throwable) {
        hideLoading()
        showError()
        Log.e(TAG, "Error fetching categories", throwable)
    }

    private fun showLoading() {
        changeState { it.copy(isLoading = true) }
    }

    private fun hideLoading() {
        changeState { it.copy(isLoading = false) }
    }

    private fun showError() {
        changeState { it.copy(isError = true) }
    }

    private fun hideError() {
        changeState { it.copy(isError = false) }
    }

    private fun showItems(listItems: List<ItemRow>) {
        changeState { it.copy(listItems = listItems) }
    }

    private fun saveResponseInSavedState(itemRows: List<ItemRow>) {
        handle[ARG_RESPONSE] = itemRows
    }

    private fun isResponseInSavedState() = handle.contains(ARG_RESPONSE)

    private fun getResponseFromSavedState(): List<ItemRow>? = handle[ARG_RESPONSE]

    companion object {
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        const val ARG_RESPONSE = "arg_response"
        private val TAG = ProductsListViewModel::class.java.simpleName
    }
}
