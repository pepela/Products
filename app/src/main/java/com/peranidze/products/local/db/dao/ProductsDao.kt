package com.peranidze.products.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peranidze.products.local.db.entity.ProductEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface ProductsDao {

    @Query("SELECT * FROM product WHERE product.product_id = :productId AND product.category_id= :categoryId")
    fun get(productId: Long, categoryId: Long): Flowable<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(restaurants: List<ProductEntity>): Completable
}
