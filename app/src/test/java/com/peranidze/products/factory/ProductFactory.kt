package com.peranidze.products.factory

import com.peranidze.products.domain.model.Product
import com.peranidze.products.factory.DataFactory.Factory.randomDouble
import com.peranidze.products.factory.DataFactory.Factory.randomInt
import com.peranidze.products.factory.DataFactory.Factory.randomLong
import com.peranidze.products.factory.DataFactory.Factory.randomUuid
import com.peranidze.products.factory.SalePriceFactory.makeSalePriceDto
import com.peranidze.products.factory.SalePriceFactory.makeSalePriceModel
import com.peranidze.products.local.db.entity.ProductEntity
import com.peranidze.products.remote.model.ProductDto

object ProductFactory {

    fun makeProductDto(): ProductDto =
        ProductDto(
            randomLong(),
            randomLong(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            makeSalePriceDto()
        )

    fun makeProductDtoList(count: Int = randomInt()): List<ProductDto> =
        List(count) { makeProductDto() }

    fun makeProductModel(): Product =
        Product(
            randomLong(),
            randomLong(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            makeSalePriceModel()
        )

    fun makeProductModelList(count: Int = randomInt()): List<Product> =
        List(count) { makeProductModel() }

    fun makeProductEntity(): ProductEntity =
        ProductEntity(
            randomLong(),
            randomLong(),
            randomLong(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomDouble(),
            randomUuid()
        )

    fun makeProductEntityList(count: Int = randomInt()): List<ProductEntity> =
        List(count) { makeProductEntity() }

}
