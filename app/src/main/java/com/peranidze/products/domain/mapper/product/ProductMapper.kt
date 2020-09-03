package com.peranidze.products.domain.mapper.product

import com.peranidze.products.domain.mapper.Mapper
import com.peranidze.products.domain.model.Product
import com.peranidze.products.domain.model.SalePrice
import com.peranidze.products.local.db.entity.ProductEntity
import com.peranidze.products.remote.model.ProductDto
import javax.inject.Inject

open class ProductMapper @Inject constructor() : Mapper<ProductDto, ProductEntity, Product> {

    override fun fromDtoToDomain(dto: ProductDto): Product =
        with(dto) {
            Product(
                id,
                categoryId,
                name,
                url,
                description,
                SalePrice(salePriceDto.amount, salePriceDto.currency)
            )
        }

    override fun fromDomainToEntity(domain: Product): ProductEntity =
        with(domain) {
            ProductEntity(
                null,
                id,
                categoryId,
                name,
                url,
                description,
                salePrice.amount,
                salePrice.currency
            )
        }

    override fun fromEntityToDomain(entity: ProductEntity): Product =
        with(entity) {
            Product(
                productId,
                categoryId,
                name,
                imageUrl,
                description,
                SalePrice(price, currency)
            )
        }
}
