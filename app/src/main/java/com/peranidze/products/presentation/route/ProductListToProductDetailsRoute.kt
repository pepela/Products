package com.peranidze.products.presentation.route

import androidx.navigation.fragment.FragmentNavigator

data class ProductListToProductDetailsRoute(
    val productId: Long,
    val categoryId: Long,
    val sharedElementId: Int,
    val fragmentNavigatorExtras: FragmentNavigator.Extras
)
