package com.peranidze.products.domain.model

data class Product(
    val id: Long,
    val categoryId: Long,
    val name: String,
    val url: String,
    val description: String?,
    val salePrice: SalePrice
)
