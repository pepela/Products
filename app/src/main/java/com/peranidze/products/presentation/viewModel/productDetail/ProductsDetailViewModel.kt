package com.peranidze.products.presentation.viewModel.productDetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.peranidze.products.domain.Repository
import com.peranidze.products.domain.model.Product
import com.peranidze.products.extension.toFullUrl
import com.peranidze.products.presentation.ViewModelAssistedFactory
import com.peranidze.products.presentation.viewModel.BaseViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductsDetailViewModel @AssistedInject constructor(
    private val repository: Repository,
    @Assisted private val handle: SavedStateHandle
) : BaseViewModel<ProductsDetailViewModel.State>(State()) {

    @AssistedInject.Factory
    interface Factory : ViewModelAssistedFactory<ProductsDetailViewModel> {
        override fun create(handle: SavedStateHandle): ProductsDetailViewModel
    }

    data class State(
        val isLoading: Boolean = false,
        val imageUrl: String? = null,
        val name: String? = null,
        val description: String? = null,
        val price: String? = null,
        val currency: String? = null
    )

    init {
        getProductId()?.let { productId ->
            getCategoryId()?.let { categoryId ->
                getProduct(productId, categoryId)
            }
        }
    }

    private fun getProduct(productId: Long, categoryId: Long) {
        repository.getProduct(productId, categoryId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading() }
            .subscribe(::getProductOnNext, ::getProductOnError)
            .addToDisposables()
    }

    private fun getProductOnNext(product: Product) {
        hideLoading()
        showProduct(product)
    }

    private fun getProductOnError(throwable: Throwable) {
        hideLoading()
        Log.e(TAG, "Error getting product", throwable)
    }

    private fun showLoading() {
        changeState { it.copy(isLoading = true) }
    }

    private fun hideLoading() {
        changeState { it.copy(isLoading = false) }
    }

    private fun showProduct(product: Product) {
        changeState {
            it.copy(
                imageUrl = product.url.toFullUrl(),
                name = product.name,
                description = product.description,
                price = formatAmount(product.salePrice.amount),
                currency = product.salePrice.currency
            )
        }
    }

    private fun formatAmount(amount: Double) = amount.toString()

    private fun getProductId(): Long? = handle[ARG_PRODUCT_ID]

    private fun getCategoryId(): Long? = handle[ARG_CATEGORY_ID]

    companion object {
        const val ARG_PRODUCT_ID = "arg_product_id"
        const val ARG_CATEGORY_ID = "arg_category_id"

        private val TAG = ProductsDetailViewModel::class.java.simpleName
    }
}
