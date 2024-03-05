package com.mirash.familiar.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

/**
 * @author Mirash
 */
@Dao
interface UserDao {
    @Transaction
    @Query("SELECT * FROM User")
    fun getUsersWithCredentials(): LiveData<UserWithCredentials>

    @Transaction
    @Query("SELECT * FROM User")
    fun getUsersWithCredentialsSync(): List<UserWithCredentials>

    @Query("SELECT * FROM User")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM User")
    fun getAllSync(): List<User>

    @Query("SELECT * FROM User WHERE id = :id")
    fun getById(id: Long): LiveData<User>

    @Query("SELECT * FROM User WHERE id = :id")
    fun getByIdSync(id: Long): User?

    @Insert
    fun insert(user: User): Long

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM User")
    fun deleteAll()
}
