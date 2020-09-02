package com.peranidze.products

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.util.DebugLogger

class ProductsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initCoil()
    }

    private fun initCoil() {
        Coil.setImageLoader(
            ImageLoader.Builder(applicationContext)
                .logger(DebugLogger())
                .build()
        )
    }
}
