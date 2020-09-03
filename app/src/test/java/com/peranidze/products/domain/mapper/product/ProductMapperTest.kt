package com.peranidze.products.domain.mapper.product

import com.peranidze.products.factory.ProductFactory.makeProductDto
import com.peranidze.products.factory.ProductFactory.makeProductEntity
import com.peranidze.products.factory.ProductFactory.makeProductModel
import org.junit.Test

class ProductMapperTest {

    private val productMapper: ProductMapper = ProductMapper()

    @Test
    fun fromDtoToDomain() {
        val productDto = makeProductDto()
        val product = productMapper.fromDtoToDomain(productDto)

        assert(productDto.id == product.id)
        assert(productDto.categoryId == product.categoryId)
        assert(productDto.name == product.name)
        assert(productDto.description == product.description)
        assert(productDto.url == product.url)
        assert(productDto.salePriceDto.amount == product.salePrice.amount)
        assert(productDto.salePriceDto.currency == product.salePrice.currency)
    }

    @Test
    fun fromDomainToEntity() {
        val productModel = makeProductModel()
        val productEntity = productMapper.fromDomainToEntity(productModel)

        assert(null == productEntity.id)
        assert(productModel.id == productEntity.productId)
        assert(productModel.categoryId == productEntity.categoryId)
        assert(productModel.name == productEntity.name)
        assert(productModel.description == productEntity.description)
        assert(productModel.url == productEntity.imageUrl)
        assert(productModel.salePrice.amount == productEntity.price)
        assert(productModel.salePrice.currency == productEntity.currency)
    }

    @Test
    fun fromEntityToDomain() {
        val productEntity = makeProductEntity()
        val product = productMapper.fromEntityToDomain(productEntity)

        assert(product.id == productEntity.productId)
        assert(product.categoryId == productEntity.categoryId)
        assert(product.name == productEntity.name)
        assert(product.description == productEntity.description)
        assert(product.url == productEntity.imageUrl)
        assert(product.salePrice.amount == productEntity.price)
        assert(product.salePrice.currency == productEntity.currency)
    }
}
