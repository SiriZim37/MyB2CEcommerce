package com.siri.myb2cecommerce.data.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.siri.myb2cecommerce.data.dao.UserDao
import com.siri.myb2cecommerce.data.dao.CardDao
import com.siri.myb2cecommerce.data.entitys.CardEntity
import com.siri.myb2cecommerce.data.dao.ProductDao
import com.siri.myb2cecommerce.data.dao.TokenDao
import com.siri.myb2cecommerce.data.entitys.ProductEntity
import com.siri.myb2cecommerce.data.entitys.TokenEntity
import com.siri.myb2cecommerce.data.entitys.UserEntity

@Database(entities = [UserEntity::class, ProductEntity::class, CardEntity::class, TokenEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun cardDao(): CardDao
    abstract fun userDao(): UserDao
    abstract fun tokenDao(): TokenDao
}