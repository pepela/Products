package com.peranidze.products.factory

import com.peranidze.products.domain.model.Category
import com.peranidze.products.factory.DataFactory.Factory.randomUuid
import com.peranidze.products.factory.ProductFactory.makeProductDtoList
import com.peranidze.products.factory.ProductFactory.makeProductModelList
import com.peranidze.products.remote.model.CategoryDto

object CategoryFactory {

    fun makeCategoryDto(): CategoryDto =
        CategoryDto(randomUuid(), randomUuid(), randomUuid(), makeProductDtoList())

    fun makeCategoryDtoList(count: Int = DataFactory.randomInt()): List<CategoryDto> =
        List(count) { makeCategoryDto() }

    fun makeCategoryModel(): Category =
        Category(randomUuid(), makeProductModelList())

    fun makeCategoryModelList(count: Int = DataFactory.randomInt()): List<Category> =
        List(count) { makeCategoryModel() }
}
