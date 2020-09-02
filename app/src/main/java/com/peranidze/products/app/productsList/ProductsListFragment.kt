package com.peranidze.products.app.productsList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.peranidze.products.R
import com.peranidze.products.app.productsList.adapter.ProductAdapter
import com.peranidze.products.databinding.FragmentProductsListBinding

class ProductsListFragment : Fragment(R.layout.fragment_products_list) {

    private val productAdapter = ProductAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(FragmentProductsListBinding.bind(view)) {
            bindInteractions(this)
        }
    }

    private fun bindInteractions(binding: FragmentProductsListBinding) {
        binding.productsRv.adapter = productAdapter.apply {
            setOnProductItemClickListener {
                navigateToProductDetail(it.id)
            }
        }
    }

    private fun navigateToProductDetail(id: Int) {
        findNavController().navigate(
            ProductsListFragmentDirections.actionProductsListFragmentToProductDetailFragment(id)
        )
    }
}
