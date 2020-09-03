package com.peranidze.products.domain

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.peranidze.products.domain.mapper.category.CategoryMapper
import com.peranidze.products.domain.mapper.product.ProductMapper
import com.peranidze.products.domain.model.Category
import com.peranidze.products.domain.model.Product
import com.peranidze.products.factory.CategoryFactory
import com.peranidze.products.factory.ProductFactory.makeProductEntity
import com.peranidze.products.factory.ProductFactory.makeProductEntityList
import com.peranidze.products.factory.ProductFactory.makeProductModel
import com.peranidze.products.local.db.dao.ProductsDao
import com.peranidze.products.local.db.entity.ProductEntity
import com.peranidze.products.remote.model.CategoryDto
import com.peranidze.products.remote.service.CategoriesService
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    @Mock
    lateinit var categoriesService: CategoriesService

    @Mock
    lateinit var productsDao: ProductsDao

    @Mock
    lateinit var categoryMapper: CategoryMapper

    @Mock
    lateinit var productMapper: ProductMapper

    @InjectMocks
    lateinit var repository: RepositoryImpl

    @Before
    fun setUp() {
        whenever(productMapper.fromEntityToDomain(any())).thenReturn(PRODUCT)
        whenever(productMapper.fromDomainListToEntityList(any())).thenReturn(PRODUCT_ENTITIES_LIST)
        whenever(categoryMapper.fromDtoListToDomainList(any())).thenReturn(CATEGORIES_LIST)
    }

    @Test
    fun `getCategories maps, saves in db and returns categories`() {
        whenever(categoriesService.categories()).thenReturn(Flowable.just(CATEGORY_DTO_LIST))
        whenever(productsDao.insertAll(any())).thenReturn(Completable.complete())
        val testObserver = repository.getCategories()
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .test()

        verify(categoryMapper).fromDtoListToDomainList(CATEGORY_DTO_LIST)

        testObserver.assertNoErrors()
        testObserver.assertValue(CATEGORIES_LIST)
    }

    @Test
    fun `getCategories with db error has no errors`() {
        whenever(categoriesService.categories()).thenReturn(Flowable.just(CATEGORY_DTO_LIST))
        whenever(productsDao.insertAll(any())).thenReturn(Completable.error(THROWABLE))
        val testObserver = repository.getCategories()
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .test()

        verify(categoryMapper).fromDtoListToDomainList(CATEGORY_DTO_LIST)

        testObserver.assertNoErrors()
        testObserver.assertValue(CATEGORIES_LIST)
    }

    @Test
    fun `getProduct maps and return product`() {
        whenever(productsDao.get(any(), any()))
            .thenReturn(Flowable.just(PRODUCT_ENTITY))
        val testObserver = repository.getProduct(PRODUCT_ID, CATEGORY_ID)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .test()

        verify(productMapper).fromEntityToDomain(PRODUCT_ENTITY)

        testObserver.assertNoErrors()
        testObserver.assertValue(PRODUCT)
    }

    @Test
    fun `getProduct with error never maps and returns error`() {
        whenever(productsDao.get(any(), any()))
            .thenReturn(Flowable.error(THROWABLE))
        val testObserver = repository.getProduct(PRODUCT_ID, CATEGORY_ID)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .test()

        verify(productMapper, never()).fromEntityToDomain(PRODUCT_ENTITY)

        testObserver.assertError(THROWABLE)
    }

    companion object {
        private const val PRODUCT_ID: Long = 1L
        private const val CATEGORY_ID: Long = 3590L

        private val PRODUCT_ENTITY: ProductEntity = makeProductEntity()
        private val PRODUCT_ENTITIES_LIST: List<ProductEntity> = makeProductEntityList()
        private val PRODUCT: Product = makeProductModel()
        private val CATEGORY_DTO_LIST: List<CategoryDto> = CategoryFactory.makeCategoryDtoList()
        private val CATEGORIES_LIST: List<Category> = CategoryFactory.makeCategoryModelList()
        private val THROWABLE: Throwable = RuntimeException("mock error")

    }
}
