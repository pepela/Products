package com.peranidze.products.remote.service

import com.peranidze.products.remote.api.CategoriesApi
import com.peranidze.products.remote.model.CategoryDto
import io.reactivex.Flowable
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class CategoriesService @Inject constructor(retrofit: Retrofit) : CategoriesApi {

    private val categoriesApi by lazy { retrofit.create(CategoriesApi::class.java) }

    override fun categories(): Flowable<List<CategoryDto>> = categoriesApi.categories()
}
