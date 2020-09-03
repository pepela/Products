package com.peranidze.products.remote.model

import com.squareup.moshi.Json

data class CategoryDto(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String?,
    @Json(name = "products") val productDtoList: List<ProductDto>
)
