package com.siri.myb2cecommerce.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siri.myb2cecommerce.data.entitys.CardEntity
import com.siri.myb2cecommerce.data.room.db.AppDatabase
import com.siri.myb2cecommerce.data.repo.CartRepo
import com.siri.myb2cecommerce.data.entitys.ProductEntity
import com.siri.myb2cecommerce.data.repo.CardRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepo
) : ViewModel() {
        val allproducts: LiveData<List<ProductEntity>> = repository.allCartProducts

//class CartViewModel (application: Application) : AndroidViewModel(application){
//
//    private val repository: CartRepo
//    val allproducts: LiveData<List<ProductEntity>>
//
//    init {
//        val productDao = AppDatabase.getInstance(application).productDao()
//        repository = CartRepo(productDao)
//        allproducts = repository.allCartProducts
//    }

    fun insert(product: ProductEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(product)
    }

    fun deleteCart(product: ProductEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(product)
    }

    fun updateCart(product: ProductEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(product)
    }
}