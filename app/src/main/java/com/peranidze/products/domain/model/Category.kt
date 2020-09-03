package com.peranidze.products.domain.model

data class Category(
    val name: String,
    val productsList: List<Product>
)
