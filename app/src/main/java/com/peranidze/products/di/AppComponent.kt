package com.peranidze.products.di

import android.app.Application
import com.peranidze.products.ProductsApplication
import com.peranidze.products.di.module.ActivityBuildersModule
import com.peranidze.products.di.module.ApplicationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityBuildersModule::class,
        ApplicationModule::class
    ]
)
interface AppComponent : AndroidInjector<ProductsApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
