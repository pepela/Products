package com.peranidze.products.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.peranidze.products.local.db.dao.ProductsDao
import com.peranidze.products.local.db.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
//@TypeConverters(ProductTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productsDao(): ProductsDao
}
