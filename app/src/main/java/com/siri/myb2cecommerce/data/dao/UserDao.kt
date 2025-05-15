package com.siri.myb2cecommerce.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import com.siri.myb2cecommerce.data.entitys.UserEntity
import com.siri.myb2cecommerce.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM users WHERE uid = :userID")
    suspend fun getUserById(userID: String): User

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM users WHERE userEmail = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?


}
