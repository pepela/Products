package com.peranidze.products.remote.api

import com.peranidze.products.remote.model.Category
import io.reactivex.Flowable
import retrofit2.http.GET

interface ConfigApi {

    @GET(CONFIG)
    fun config(): Flowable<List<Category>>

    companion object {
        private const val CONFIG = "/"
    }
}
