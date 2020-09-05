package com.peranidze.products.presentation.mapper

import androidx.annotation.VisibleForTesting
import com.peranidze.products.app.productsList.adapter.ItemRow
import com.peranidze.products.domain.model.Category
import com.peranidze.products.domain.model.Product
import com.peranidze.products.extension.toFullUrl
import javax.inject.Inject

open class ItemRowMapper @Inject constructor() {

    fun mapToItemRowsList(categoriesList: List<Category>): List<ItemRow> =
        with(mutableListOf<ItemRow>()) {
            categoriesList.forEach { category ->
                add(mapToCategoryItem(category))
                addAll(category.productsList.map { product -> mapToProductItem(product) })
            }
            this
        }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun mapToCategoryItem(category: Category): ItemRow = ItemRow.CategoryItem(category.name)

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun mapToProductItem(product: Product): ItemRow =
        with(product) {
            ItemRow.ProductItem(
                id,
                categoryId,
                name,
                description,
                url.toFullUrl(),
                salePrice.amount.toString(),
                salePrice.currency
            )
        }
}
