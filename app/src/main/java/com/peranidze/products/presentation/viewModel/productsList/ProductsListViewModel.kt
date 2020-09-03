package com.peranidze.products.presentation.viewModel.productsList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.peranidze.products.app.productsList.adapter.ItemRow
import com.peranidze.products.domain.Repository
import com.peranidze.products.domain.model.Category
import com.peranidze.products.domain.model.Product
import com.peranidze.products.extension.toFullUrl
import com.peranidze.products.presentation.Event
import com.peranidze.products.presentation.ViewModelAssistedFactory
import com.peranidze.products.presentation.viewModel.BaseViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductsListViewModel @AssistedInject constructor(
    private val repository: Repository,
    @Assisted private val handle: SavedStateHandle
) : BaseViewModel<ProductsListViewModel.State>(State()) {

    @AssistedInject.Factory
    interface Factory : ViewModelAssistedFactory<ProductsListViewModel> {
        override fun create(handle: SavedStateHandle): ProductsListViewModel
    }

    data class State(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val listItems: List<ItemRow> = emptyList()
    )

    private val _navigationToDetailEvent = MutableLiveData<Event<Pair<Long, Long>>>()
    val navigationToDetailEvent: LiveData<Event<Pair<Long, Long>>>
        get() = _navigationToDetailEvent

    init {
        fetchCategories()
    }

    fun onProductItemClicked(id: Long, categoryId: Long) {
        _navigationToDetailEvent.postValue(Event(id to categoryId))
    }

    private fun fetchCategories() {
        repository.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::fetchCategoriesOnNext, ::fetchCategoriesOnError)
            .addToDisposables()
    }

    private fun fetchCategoriesOnNext(categoriesList: List<Category>) {
        hideLoading()
        hideError()
        changeState { it.copy(listItems = mapToItemRows(categoriesList)) }
    }

    private fun fetchCategoriesOnError(throwable: Throwable) {
        hideLoading()
        showError()
        Log.e(TAG, "Error fetching categories", throwable)
    }

    private fun mapToItemRows(categoriesList: List<Category>): List<ItemRow> =
        with(mutableListOf<ItemRow>()) {
            categoriesList.forEach { category ->
                add(mapToCategoryItem(category))
                addAll(category.productsList.map { product -> mapToProductItem(product) })
            }
            this
        }

    private fun mapToCategoryItem(category: Category): ItemRow = ItemRow.CategoryItem(category.name)

    private fun mapToProductItem(product: Product): ItemRow =
        with(product) {
            ItemRow.ProductItem(
                id,
                categoryId,
                name,
                description.orEmpty(),
                url.toFullUrl(),
                salePrice.amount.toString(),
                salePrice.currency
            )
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

    companion object {
        private val TAG = ProductsListViewModel::class.java.simpleName
    }
}
