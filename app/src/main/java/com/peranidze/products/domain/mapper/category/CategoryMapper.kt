package com.peranidze.products.domain.mapper.category

import com.peranidze.products.domain.mapper.Mapper
import com.peranidze.products.domain.mapper.product.ProductMapper
import com.peranidze.products.domain.model.Category
import com.peranidze.products.remote.model.CategoryDto
import javax.inject.Inject

open class CategoryMapper @Inject constructor(private val productMapper: ProductMapper) :
    Mapper<CategoryDto, Unit, Category> {

    override fun fromDtoToDomain(dto: CategoryDto): Category =
        Category(dto.name, productMapper.fromDtoListToDomainList(dto.productDtoList))

    override fun fromDomainToEntity(domain: Category): Unit {
        throw IllegalArgumentException("Category has no entity class")
    }

    override fun fromEntityToDomain(entity: Unit): Category {
        throw IllegalArgumentException("Category has no entity class")
    }

}
