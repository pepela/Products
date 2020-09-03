package com.peranidze.products.app.productDetail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.peranidze.products.R
import com.peranidze.products.databinding.FragmentProductDetailBinding
import dagger.android.support.DaggerFragment

class ProductDetailFragment : DaggerFragment(R.layout.fragment_product_detail) {

    private val args: ProductDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(FragmentProductDetailBinding.bind(view)) {
        }
    }
}
