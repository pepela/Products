package com.peranidze.products.app.productsList.adapter

sealed class ItemRow(val itemType: ItemType) {

    data class CategoryItem(val name: String) : ItemRow(ItemType.CATEGORY)

    data class ProductItem(
        val id: Int,
        val name: String,
        val description: String,
        val imageUrl: String,
        val price: String,
        val currency: String
    ) : ItemRow(ItemType.PRODUCT)
}
