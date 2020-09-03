package com.peranidze.products.app.base

import androidx.annotation.LayoutRes
import com.peranidze.products.presentation.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : DaggerFragment(contentLayoutId) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
}
