package com.siri.myb2cecommerce.data.repo

import androidx.lifecycle.LiveData
import com.siri.myb2cecommerce.data.dao.CardDao
import com.siri.myb2cecommerce.data.entitys.CardEntity
import javax.inject.Inject

//class CardRepo(private val cardDao: CardDao) {

class CardRepo @Inject constructor(private val cardDao: CardDao) {
    val allCards: LiveData<List<CardEntity>> = cardDao.getAll()

    suspend fun insert(cardEntity: CardEntity) {
        cardDao.insert(cardEntity)
    }
    suspend fun delete(cardEntity: CardEntity) {
        cardDao.delete(cardEntity)
    }
    suspend fun update(cardEntity: CardEntity) {
        cardDao.update(cardEntity)
    }
}