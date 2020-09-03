package com.peranidze.products.di.module

import androidx.lifecycle.ViewModel
import com.peranidze.products.di.mapKey.ViewModelKey
import com.peranidze.products.presentation.ViewModelAssistedFactory
import com.peranidze.products.presentation.viewModel.productDetail.ProductsDetailViewModel
import com.peranidze.products.presentation.viewModel.productsList.ProductsListViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@AssistedModule
@Module(includes = [AssistedInject_ViewModelModule::class])
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProductsListViewModel::class)
    internal abstract fun bindProductsListViewModelFactory(viewModelFactory: ProductsListViewModel.Factory)
            : ViewModelAssistedFactory<out ViewModel>

    @Binds
    @IntoMap
    @ViewModelKey(ProductsDetailViewModel::class)
    internal abstract fun bindProductsDetailViewModelFactory(viewModelFactory: ProductsDetailViewModel.Factory)
            : ViewModelAssistedFactory<out ViewModel>

}
