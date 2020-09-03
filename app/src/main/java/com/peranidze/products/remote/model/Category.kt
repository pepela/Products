package com.peranidze.products.remote.model

data class Category(
    val id: String,
    val name: String,
    val description: String?,
    val products: List<Product>
)
