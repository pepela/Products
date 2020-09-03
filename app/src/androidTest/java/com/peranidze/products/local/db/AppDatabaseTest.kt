package com.peranidze.products.local.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.peranidze.products.local.db.dao.ProductsDao
import com.peranidze.products.local.db.entity.ProductEntity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var productsDao: ProductsDao
    private lateinit var db: AppDatabase

    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        productsDao = db.productsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeProductAndReadIt() {
        productsDao.insertAll(listOf(PRODUCT_1, PRODUCT_2)).blockingAwait()

        val testObserver = productsDao.get(PRODUCT_ID_1, CATEGORY_ID_1).test()

        testObserver.assertValue(PRODUCT_1)
        testObserver.assertNoErrors()
    }

    @Test
    @Throws(Exception::class)
    fun writeProductAndReadOtherIt() {
        productsDao.insertAll(listOf(PRODUCT_1)).blockingAwait()

        val testObserver = productsDao.get(PRODUCT_ID_2, CATEGORY_ID_2).test()

        testObserver.assertNoValues()
        testObserver.assertNoErrors()
    }

    companion object {

        private const val PRODUCT_ID_1 = 2L
        private const val CATEGORY_ID_1 = 3L

        private const val PRODUCT_ID_2 = 20L
        private const val CATEGORY_ID_2 = 30L

        private val PRODUCT_1 =
            ProductEntity(
                1L,
                PRODUCT_ID_1,
                CATEGORY_ID_1,
                "mock_bread",
                "/mock_url",
                "mock_description",
                5.6,
                "EUR"
            )

        private val PRODUCT_2 =
            ProductEntity(
                10L,
                PRODUCT_ID_2,
                CATEGORY_ID_2,
                "mock_milk",
                "/mock_url_2",
                "mock_description_2",
                50.6,
                "USD"
            )

    }
}
