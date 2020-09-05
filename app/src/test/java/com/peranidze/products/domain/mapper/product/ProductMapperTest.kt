package com.peranidze.products.domain.mapper.product

import com.peranidze.products.factory.ProductFactory.makeProductDto
import com.peranidze.products.factory.ProductFactory.makeProductEntity
import com.peranidze.products.factory.ProductFactory.makeProductModel
import org.junit.Assert.assertTrue
import org.junit.Test

class ProductMapperTest {

    private val productMapper: ProductMapper = ProductMapper()

    @Test
    fun fromDtoToDomain() {
        val productDto = makeProductDto()
        val product = productMapper.fromDtoToDomain(productDto)

        assertTrue(productDto.id == product.id)
        assertTrue(productDto.categoryId == product.categoryId)
        assertTrue(productDto.name == product.name)
        assertTrue(productDto.description == product.description)
        assertTrue(productDto.url == product.url)
        assertTrue(productDto.salePriceDto.amount == product.salePrice.amount)
        assertTrue(productDto.salePriceDto.currency == product.salePrice.currency)
    }

    @Test
    fun fromDomainToEntity() {
        val productModel = makeProductModel()
        val productEntity = productMapper.fromDomainToEntity(productModel)

        assertTrue(null == productEntity.id)
        assertTrue(productModel.id == productEntity.productId)
        assertTrue(productModel.categoryId == productEntity.categoryId)
        assertTrue(productModel.name == productEntity.name)
        assertTrue(productModel.description == productEntity.description)
        assertTrue(productModel.url == productEntity.imageUrl)
        assertTrue(productModel.salePrice.amount == productEntity.price)
        assertTrue(productModel.salePrice.currency == productEntity.currency)
    }

    @Test
    fun fromEntityToDomain() {
        val productEntity = makeProductEntity()
        val product = productMapper.fromEntityToDomain(productEntity)

        assertTrue(product.id == productEntity.productId)
        assertTrue(product.categoryId == productEntity.categoryId)
        assertTrue(product.name == productEntity.name)
        assertTrue(product.description == productEntity.description)
        assertTrue(product.url == productEntity.imageUrl)
        assertTrue(product.salePrice.amount == productEntity.price)
        assertTrue(product.salePrice.currency == productEntity.currency)
    }
}
