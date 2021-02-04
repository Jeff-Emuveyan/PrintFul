package com.seamfix.printful.datasource.local.dao

import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.room.*
import com.seamfix.printful.model.User

@Keep
@Dao
interface UserDao {

    @Insert
    suspend fun saveUser(user: User)

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUserByID(id: String): User?

    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<User>?

    @Update
    suspend fun updateUser(user: User)
}