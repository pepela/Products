package com.peranidze.products.app.productsList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.peranidze.products.R
import com.peranidze.products.app.productsList.adapter.ItemType.*
import com.peranidze.products.databinding.ItemCategoryBinding
import com.peranidze.products.databinding.ItemProductBinding
import javax.inject.Inject

class ProductAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<ItemRow> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var onProductItemClickListener: ((ItemRow.ProductItem, FragmentNavigator.Extras) -> Unit)? =
        null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (values()[viewType]) {
            CATEGORY -> CategoryViewHolder(
                ItemCategoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            PRODUCT -> ProductViewHolder(
                ItemProductBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    override fun getItemViewType(position: Int): Int = items[position].itemType.ordinal

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is ItemRow.CategoryItem -> (holder as CategoryViewHolder).bind(item)
            is ItemRow.ProductItem -> (holder as ProductViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    fun setOnProductItemClickListener(listener: (ItemRow.ProductItem, FragmentNavigator.Extras) -> Unit) {
        onProductItemClickListener = listener
    }

    class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemRow.CategoryItem) {
            binding.nameTv.text = item.name
        }
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemRow.ProductItem) {
            with(binding) {
                imageIv.load(item.imageUrl) {
                    placeholder(R.drawable.ic_photo)
                    error(R.drawable.ic_broken_image)
                }
                nameTv.text = item.name
                descriptionTv.text = item.description
                priceTv.text = item.price
                currencyTv.text = item.currency
            }
            itemView.setOnClickListener {
                val fragmentNavigatorExtras = createFragmentNavigatorExtras(item)
                onProductItemClickListener?.invoke(item, fragmentNavigatorExtras)
            }
            setTransitionNames(item)
        }

        private fun createFragmentNavigatorExtras(item: ItemRow.ProductItem) =
            with(item.getSharedElementId()) {
                FragmentNavigatorExtras(
                    binding.imageIv to "image_$this",
                    binding.nameTv to "name_$this",
                    binding.descriptionTv to "description_$this",
                    binding.priceTv to "price_$this",
                    binding.currencyTv to "currency_$this"
                )
            }

        private fun setTransitionNames(item: ItemRow.ProductItem) {
            with(item.getSharedElementId()) {
                ViewCompat.setTransitionName(binding.imageIv, "image_$this")
                ViewCompat.setTransitionName(binding.nameTv, "name_$this")
                ViewCompat.setTransitionName(binding.descriptionTv, "description_$this")
                ViewCompat.setTransitionName(binding.priceTv, "price_$this")
                ViewCompat.setTransitionName(binding.currencyTv, "currency_$this")
            }
        }
    }
}
