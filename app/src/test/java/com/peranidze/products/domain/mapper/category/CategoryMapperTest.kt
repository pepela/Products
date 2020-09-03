package com.peranidze.products.domain.mapper.category

import com.nhaarman.mockito_kotlin.mock
import com.peranidze.products.domain.mapper.product.ProductMapper
import com.peranidze.products.factory.CategoryFactory.makeCategoryDto
import com.peranidze.products.factory.CategoryFactory.makeCategoryModel
import org.junit.Test

class CategoryMapperTest {

    private val productMapper: ProductMapper = mock()

    private val categoryMapper: CategoryMapper = CategoryMapper(productMapper)

    @Test
    fun fromDtoToDomain() {
        val categoryDto = makeCategoryDto()
        val category = categoryMapper.fromDtoToDomain(categoryDto)

        assert(category.name == categoryDto.name)
    }

    @Test(expected = IllegalArgumentException::class)
    fun fromDomainToEntity() {
        val categoryModel = makeCategoryModel()
        categoryMapper.fromDomainToEntity(categoryModel)
    }

    @Test(expected = IllegalArgumentException::class)
    fun fromEntityToDomain() {
        categoryMapper.fromEntityToDomain(Unit)
    }
}
