package com.peranidze.products.app.productDetail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import coil.load
import com.peranidze.products.R
import com.peranidze.products.app.base.BaseFragment
import com.peranidze.products.databinding.FragmentProductDetailBinding
import com.peranidze.products.presentation.extension.mapDistinct
import com.peranidze.products.presentation.viewModel.productDetail.ProductsDetailViewModel
import com.peranidze.products.presentation.viewModel.productDetail.ProductsDetailViewModel.Companion.ARG_CATEGORY_ID
import com.peranidze.products.presentation.viewModel.productDetail.ProductsDetailViewModel.Companion.ARG_PRODUCT_ID
import com.peranidze.products.presentation.withFactory

class ProductDetailFragment : BaseFragment(R.layout.fragment_product_detail) {

    private val args: ProductDetailFragmentArgs by navArgs()

    private val viewModel: ProductsDetailViewModel by viewModels {
        withFactory(
            viewModelFactory,
            bundleOf(ARG_PRODUCT_ID to args.productId, ARG_CATEGORY_ID to args.categoryId)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(FragmentProductDetailBinding.bind(view)) {
            observeState(this)
            setSharedElementTransitionNames(this)
        }
    }

    private fun observeState(binding: FragmentProductDetailBinding) {
        with(binding) {
            viewModel.state.mapDistinct { it.isLoading }.observe(viewLifecycleOwner, {
                loadingPb.isVisible = it
            })
            viewModel.state.mapDistinct { it.imageUrl }.observe(viewLifecycleOwner, {
                imageIv.load(it) { error(R.drawable.ic_broken_image) }
            })
            viewModel.state.mapDistinct { it.name }.observe(viewLifecycleOwner, {
                nameTv.text = it
            })
            viewModel.state.mapDistinct { it.description }.observe(viewLifecycleOwner, {
                descriptionTv.text = it
            })
            viewModel.state.mapDistinct { it.price }.observe(viewLifecycleOwner, {
                priceTv.text = it
            })
            viewModel.state.mapDistinct { it.currency }.observe(viewLifecycleOwner, {
                currencyTv.text = it
            })
        }
    }

    private fun setSharedElementTransitionNames(binding: FragmentProductDetailBinding) {
        with(args.sharedElementId) {
            ViewCompat.setTransitionName(binding.imageIv, "image_$this")
            ViewCompat.setTransitionName(binding.nameTv, "name_$this")
            ViewCompat.setTransitionName(binding.descriptionTv, "description_$this")
            ViewCompat.setTransitionName(binding.priceTv, "price_$this")
            ViewCompat.setTransitionName(binding.currencyTv, "currency_$this")
        }
    }
}
