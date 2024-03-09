package com.mirash.familiar.db

import androidx.lifecycle.LiveData

/**
 * @author Mirash
 */
interface IRepository<T> {
    fun insert(entity: T) : Long

    fun update(entity: T)

    fun delete(entity: T)

    fun deleteById(id: Long)

    fun getById(id: Long): LiveData<T>

    fun getByIdSync(id: Long): T?
}