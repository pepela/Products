package com.peranidze.products.remote.model

data class Product(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val url: String?,
    val description: String?,
    val salePrice: SalePrice
)
