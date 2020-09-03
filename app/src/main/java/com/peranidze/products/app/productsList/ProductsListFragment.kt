package com.peranidze.products.app.productsList

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.peranidze.products.R
import com.peranidze.products.app.productsList.adapter.ProductAdapter
import com.peranidze.products.databinding.FragmentProductsListBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ProductsListFragment : DaggerFragment(R.layout.fragment_products_list) {

    @Inject
    lateinit var productAdapter: ProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(FragmentProductsListBinding.bind(view)) {
            bindInteractions(this)
        }
    }

    private fun bindInteractions(binding: FragmentProductsListBinding) {
        binding.productsRv.adapter = productAdapter.apply {
            setOnProductItemClickListener {
                navigateToProductDetail(it.id, it.categoryId)
            }
        }
    }

    private fun navigateToProductDetail(id: Long, categoryId: Long) {
        findNavController().navigate(
            ProductsListFragmentDirections.actionProductsListFragmentToProductDetailFragment(
                id,
                categoryId
            )
        )
    }
}
