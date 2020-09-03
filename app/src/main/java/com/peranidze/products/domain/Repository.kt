package com.peranidze.products.domain

import com.peranidze.products.domain.model.Category
import com.peranidze.products.domain.model.Product
import io.reactivex.Flowable

interface Repository {

    fun getCategories(): Flowable<List<Category>>

    fun getProduct(productId: Long, categoryId: Long): Flowable<Product>
}
