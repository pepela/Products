package com.peranidze.products.di.module

import com.peranidze.products.app.productDetail.ProductDetailFragment
import com.peranidze.products.app.productsList.ProductsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeProductsListFragment(): ProductsListFragment

    @ContributesAndroidInjector
    abstract fun contributeProductDetailFragment(): ProductDetailFragment
}
