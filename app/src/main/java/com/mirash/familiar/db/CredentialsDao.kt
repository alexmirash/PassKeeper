package com.mirash.familiar.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * @author Mirash
 */
@Dao
interface CredentialsDao {
    @Query("SELECT * FROM Credentials ORDER BY position ASC")
    fun getAll(): LiveData<List<Credentials>>

    @Query("SELECT * FROM Credentials ORDER BY position ASC")
    fun getAllSync(): List<Credentials>

    @Query("SELECT * FROM Credentials WHERE userId= :userId ORDER BY position ASC")
    fun getAllByUserId(userId: Long): LiveData<List<Credentials>>

    @Query("SELECT * FROM Credentials WHERE id= :id")
    fun getById(id: Long): LiveData<Credentials>

    @Query("SELECT * FROM Credentials WHERE id= :id")
    fun getByIdSync(id: Long): Credentials

    @Query("DELETE FROM Credentials WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT * FROM Credentials WHERE userId = :userId AND position > :position")
    fun getCredentialsUnderPositionByUserIdSync(position: Int, userId: Long): List<Credentials>

    @Insert
    fun insertAll(entries: List<Credentials>)

    @Insert
    fun insert(credentials: Credentials)

    @Update
    fun update(credentials: Credentials)

    @Update
    fun updateAll(entries: List<Credentials>)

    @Delete
    fun delete(credentials: Credentials)

    @Delete
    fun delete(credentials: List<Credentials>)

    @Query("DELETE FROM Credentials")
    fun deleteAll()
}
