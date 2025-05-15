package com.siri.myb2cecommerce.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siri.myb2cecommerce.data.dao.UserDao
import com.siri.myb2cecommerce.data.entitys.UserEntity
import com.siri.myb2cecommerce.data.entitys.toUser
import com.siri.myb2cecommerce.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userDao: UserDao
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    fun getUserData(uid: String) {
        viewModelScope.launch {
            _user.value = userDao.getUserById(uid) // ให้ _user ได้รับข้อมูลจาก Dao
        }
    }

    fun getUserByEmail(email: String) {
        viewModelScope.launch {
            val userEntity = userDao.getUserByEmail(email)
            if (userEntity != null) {
                _user.value = userEntity.toUser()
            } else {

            }
        }
    }

    fun saveUserToDatabase(user: User) = viewModelScope.launch {
        val userEntity = UserEntity(
            uid = user.uid,
            userName = user.userName,
            userImage = user.userImage,
            userEmail = user.userEmail,
            userAddress = user.userAddress,
            userPhone = user.userPhone
        )
        userDao.insert(userEntity)
    }


    // todo
/*    fun updateUserImage(uid: String, imageUrl: String) {
        viewModelScope.launch {
            userDao.updateUserImage(uid, imageUrl)
        }
    }*/
}
