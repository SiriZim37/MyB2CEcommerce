package com.siri.myb2cecommerce.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siri.myb2cecommerce.data.room.db.AppDatabase
import com.siri.myb2cecommerce.data.entitys.CardEntity
import com.siri.myb2cecommerce.data.repo.CardRepo
import com.siri.myb2cecommerce.data.repo.CartRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CardViewModel @Inject constructor(
    private val repository: CardRepo
) : ViewModel() {
    val allCards: LiveData<List<CardEntity>> = repository.allCards


//class CardViewModel(application: Application): AndroidViewModel(application) {
//
//    private val repository: CardRepo
//    val allCards: LiveData<List<CardEntity>>
//
//    init {
//        val cardDao = AppDatabase.getInstance(application).cardDao()
//        repository = CardRepo(cardDao)
//        allCards = repository.allCards
//    }

    fun insert(cardEntity: CardEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(cardEntity)
    }

    fun deleteCart(cardEntity: CardEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(cardEntity)
    }

    fun updateCart(cardEntity: CardEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(cardEntity)
    }
}