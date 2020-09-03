package com.peranidze.products.app.productsList

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.peranidze.products.R
import com.peranidze.products.app.productsList.adapter.ProductAdapter
import com.peranidze.products.databinding.FragmentProductsListBinding
import com.peranidze.products.remote.service.ConfigService
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ProductsListFragment : DaggerFragment(R.layout.fragment_products_list) {

    @Inject
    lateinit var productAdapter: ProductAdapter

    @Inject
    lateinit var service: ConfigService

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
