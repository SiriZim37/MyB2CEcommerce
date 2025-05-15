package com.siri.myb2cecommerce.data.repo

import androidx.lifecycle.LiveData
import com.siri.myb2cecommerce.data.dao.UserDao
import com.siri.myb2cecommerce.data.model.User
import javax.inject.Inject


class UserRepository @Inject constructor(private val userDao: UserDao) {
    suspend fun getUserIdByName(name: String): User {
        return userDao.getUserById(name)
    }
}

