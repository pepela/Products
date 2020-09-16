package com.peranidze.products.app.productsList.adapter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class ItemRow(val itemType: ItemType) {

    @Parcelize
    data class CategoryItem(val name: String) : ItemRow(ItemType.CATEGORY), Parcelable

    @Parcelize
    data class ProductItem(
        val id: Long,
        val categoryId: Long,
        val name: String,
        val description: String?,
        val imageUrl: String,
        val price: String,
        val currency: String
    ) : ItemRow(ItemType.PRODUCT), Parcelable

    fun getSharedElementId() = hashCode()
}
