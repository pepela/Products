package com.peranidze.products.app.productsList.adapter

sealed class ItemRow(val itemType: ItemType) {

    data class CategoryItem(val name: String) : ItemRow(ItemType.CATEGORY)

    data class ProductItem(
        val id: Long,
        val categoryId: Long,
        val name: String,
        val description: String?,
        val imageUrl: String,
        val price: String,
        val currency: String
    ) : ItemRow(ItemType.PRODUCT)

    fun getSharedElementId() = hashCode()
}
