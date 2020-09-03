package com.peranidze.products.remote.service

import com.peranidze.products.remote.api.ConfigApi
import com.peranidze.products.remote.model.Category
import io.reactivex.Flowable
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigService @Inject constructor(retrofit: Retrofit) : ConfigApi {

    private val configApi by lazy { retrofit.create(ConfigApi::class.java) }

    override fun config(): Flowable<List<Category>> = configApi.config()
}
