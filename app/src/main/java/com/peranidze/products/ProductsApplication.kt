package com.peranidze.products

import coil.Coil
import coil.ImageLoader
import com.peranidze.products.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class ProductsApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        initCoil()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().application(this).build()

    private fun initCoil() {
        Coil.setImageLoader(ImageLoader.Builder(applicationContext).build())
    }
}
