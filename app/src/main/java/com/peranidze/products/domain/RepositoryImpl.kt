package com.peranidze.products.domain

import com.peranidze.products.domain.mapper.category.CategoryMapper
import com.peranidze.products.domain.mapper.product.ProductMapper
import com.peranidze.products.domain.model.Category
import com.peranidze.products.domain.model.Product
import com.peranidze.products.local.db.dao.ProductsDao
import com.peranidze.products.local.db.entity.ProductEntity
import com.peranidze.products.remote.service.CategoriesService
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val categoriesService: CategoriesService,
    private val productsDao: ProductsDao,
    private val categoryMapper: CategoryMapper,
    private val productMapper: ProductMapper
) :
    Repository {

    override fun getCategories(): Flowable<List<Category>> =
        categoriesService.categories()
            .map { categoryMapper.fromDtoListToDomainList(it) }
            .doOnNext { saveProducts(it).subscribe() }

    override fun getProduct(productId: Long, categoryId: Long): Flowable<Product> =
        productsDao.get(productId, categoryId)
            .map { productMapper.fromEntityToDomain(it) }

    private fun saveProducts(categoriesList: List<Category>): Completable =
        with(mutableListOf<ProductEntity>()) {
            categoriesList.forEach { category ->
                addAll(productMapper.fromDomainListToEntityList(category.productsList))
            }
            productsDao.insertAll(this)
        }
}
