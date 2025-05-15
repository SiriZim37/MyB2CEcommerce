package com.siri.myb2cecommerce.data.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tokens")
data class TokenEntity(
    @PrimaryKey val id: String = "singleton_token",
    val token: String
)
