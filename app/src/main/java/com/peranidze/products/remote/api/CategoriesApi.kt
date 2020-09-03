package com.peranidze.products.remote.api

import com.peranidze.products.remote.model.CategoryDto
import io.reactivex.Flowable
import retrofit2.http.GET

interface CategoriesApi {

    @GET(CATEGORIES)
    fun categories(): Flowable<List<CategoryDto>>

    companion object {
        private const val CATEGORIES = "/"
    }
}
