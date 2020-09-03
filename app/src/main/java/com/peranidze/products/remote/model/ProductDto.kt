package com.peranidze.products.remote.model

import com.squareup.moshi.Json

data class ProductDto(
    @Json(name = "id") val id: Long,
    @Json(name = "categoryId") val categoryId: Long,
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String,
    @Json(name = "description") val description: String?,
    @Json(name = "salePrice") val salePriceDto: SalePriceDto
)
