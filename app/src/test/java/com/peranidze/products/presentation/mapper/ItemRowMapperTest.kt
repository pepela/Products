package com.peranidze.products.presentation.mapper

import com.peranidze.products.app.productsList.adapter.ItemRow
import com.peranidze.products.extension.toFullUrl
import com.peranidze.products.factory.CategoryFactory.makeCategoryModel
import com.peranidze.products.factory.CategoryFactory.makeCategoryModelList
import com.peranidze.products.factory.ProductFactory.makeProductModel
import org.junit.Assert.assertTrue
import org.junit.Test

class ItemRowMapperTest {

    private val itemRowMapper = ItemRowMapper()

    @Test
    fun mapToItemRows() {
        val categoriesList = makeCategoryModelList(10)
        val itemRowsList = itemRowMapper.mapToItemRowsList(categoriesList)

        assertTrue(categoriesList.sumBy { 1 + it.productsList.size } == itemRowsList.size)
        assertTrue(itemRowsList.first() is ItemRow.CategoryItem)
        assertTrue(itemRowsList[categoriesList.first().productsList.size + 1] is ItemRow.CategoryItem)
    }

    @Test
    fun mapToCategoryItem() {
        val category = makeCategoryModel()
        val categoryItemRow = itemRowMapper.mapToCategoryItem(category)

        assertTrue(categoryItemRow is ItemRow.CategoryItem)
        assertTrue(category.name == (categoryItemRow as ItemRow.CategoryItem).name)
    }

    @Test
    fun mapToProductItem() {
        val product = makeProductModel()
        val productItemRow = itemRowMapper.mapToProductItem(product)

        assertTrue(productItemRow is ItemRow.ProductItem)
        assertTrue(product.name == (productItemRow as ItemRow.ProductItem).name)
        assertTrue(product.description == productItemRow.description)
        assertTrue(product.url.toFullUrl() == productItemRow.imageUrl)
        assertTrue(product.id == productItemRow.id)
        assertTrue(product.categoryId == productItemRow.categoryId)
        assertTrue(product.salePrice.amount.toString() == productItemRow.price)
        assertTrue(product.salePrice.currency == productItemRow.currency)
    }

}
