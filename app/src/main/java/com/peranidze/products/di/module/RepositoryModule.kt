package com.peranidze.products.di.module

import com.peranidze.products.domain.Repository
import com.peranidze.products.domain.RepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindProductsRepository(productsRepository: RepositoryImpl): Repository
}
