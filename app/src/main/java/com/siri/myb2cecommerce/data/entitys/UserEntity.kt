package com.siri.myb2cecommerce.data.entitys

import androidx.databinding.adapters.Converters
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.siri.myb2cecommerce.data.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val uid: String, // Unique User ID
    val userName: String = "",
    val userImage:String = "",
    val userEmail:String = "",
    val userAddress:String = "",
    val userPhone: String = ""
)

fun UserEntity.toUser(): User {
    return User(
        uid = this.uid,
        userName = this.userName,
        userEmail = this.userEmail,
        userImage = this.userImage,
        userAddress = this.userAddress,
        userPhone = this.userPhone
    )
}