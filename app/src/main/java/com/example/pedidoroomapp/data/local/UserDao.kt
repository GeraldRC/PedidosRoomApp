package com.example.pedidoroomapp.data.local

import androidx.room.*
import com.example.pedidoroomapp.data.model.User

@Dao
interface UserDao {

    @Transaction
    @Query("SELECT * FROM USER WHERE USER.user= :user and USER.pass= :pass")
    suspend fun getUser(user: String,pass: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)
}