package com.siri.myb2cecommerce.data.room.db

import android.app.Application
import androidx.room.Room
import com.siri.myb2cecommerce.data.dao.CardDao
import com.siri.myb2cecommerce.data.dao.ProductDao
import com.siri.myb2cecommerce.data.dao.TokenDao
import com.siri.myb2cecommerce.data.dao.UserDao
import com.siri.myb2cecommerce.data.repo.CardRepo
import com.siri.myb2cecommerce.data.repo.CartRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "b2c_db" // ใช้ชื่อฐานข้อมูลที่คุณต้องการ
        ).build()
    }

//    @Provides
//    fun provideUserDao(database: AppDatabase): UserDao {
//        return database.userDao()
//    }

    // Provide UserDao from the Room database
    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    // Provide ProductDao from the Room database
    @Provides
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }

    // Provide CardDao from the Room database
    @Provides
    fun provideCardDao(database: AppDatabase): CardDao {
        return database.cardDao()
    }
    // Provide TokenDao from the Room database
    @Provides
    @Singleton
    fun provideTokenDao(appDatabase: AppDatabase): TokenDao {
        return appDatabase.tokenDao()
    }

    // Provide CartRepo, which requires ProductDao for dependency
    @Provides
    fun provideCartRepo(productDao: ProductDao): CartRepo {
        return CartRepo(productDao)
    }

    // Provide CardRepo, which requires CardDao for dependency
    @Provides
    fun provideCardRepo(cardDao: CardDao): CardRepo {
        return CardRepo(cardDao)
    }



}

