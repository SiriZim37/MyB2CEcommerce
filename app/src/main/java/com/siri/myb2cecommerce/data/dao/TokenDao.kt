package com.siri.myb2cecommerce.data.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.siri.myb2cecommerce.data.entitys.TokenEntity

@Dao
interface TokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToken(tokenEntity: TokenEntity)

    @Query("SELECT * FROM tokens WHERE id = :id")
    suspend fun getTokenById(id: String = "singleton_token"): TokenEntity?
}
