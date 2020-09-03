package com.peranidze.products.di.module

import android.app.Application
import androidx.room.Room
import com.peranidze.products.local.db.AppDatabase
import com.peranidze.products.local.db.dao.ProductsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(context: Application): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "products-db"
        ).build()

    @Singleton
    @Provides
    fun provideProductsDao(appDatabase: AppDatabase): ProductsDao = appDatabase.productsDao()
}
