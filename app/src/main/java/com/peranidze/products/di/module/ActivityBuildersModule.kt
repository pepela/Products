package com.peranidze.products.di.module

import com.peranidze.products.app.MainActivity
import com.peranidze.products.di.scope.MainScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @MainScope
    @ContributesAndroidInjector(
        modules = [FragmentBuildersModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity
}
