package com.siri.myb2cecommerce.data.repo

import androidx.lifecycle.LiveData
import com.siri.myb2cecommerce.data.dao.CardDao
import com.siri.myb2cecommerce.data.dao.ProductDao
import com.siri.myb2cecommerce.data.entitys.ProductEntity
import javax.inject.Inject


//class CartRepo(private val productDao: ProductDao) {
class CartRepo @Inject constructor(private val productDao: ProductDao) {

    val allCartProducts: LiveData<List<ProductEntity>> = productDao.getAll()

    suspend fun insert(product: ProductEntity) {
        productDao.insert(product)
    }
    suspend fun delete(product: ProductEntity) {
        productDao.delete(product)
    }
    suspend fun update(product: ProductEntity) {
        productDao.update(product)
    }
}