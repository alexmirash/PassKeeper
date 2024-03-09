package com.mirash.familiar.db

import androidx.lifecycle.LiveData
import com.mirash.familiar.user.UserControl

/**
 * @author Mirash
 */
class CredentialsRepository(database: FamiliarDatabase) : IRepository<Credentials> {
    private val dao: CredentialsDao = database.getCredentialsDao()

    override fun insert(entity: Credentials): Long = dao.insert(entity)

    override fun update(entity: Credentials) {
        dao.update(entity)
    }

    override fun delete(entity: Credentials) {
        dao.delete(entity)
    }

    override fun deleteById(id: Long) {
        dao.deleteById(id)
    }

    override fun getById(id: Long): LiveData<Credentials> = dao.getById(id)

    override fun getByIdSync(id: Long): Credentials? = dao.getByIdSync(id)

    fun insertAll(credentialsList: List<Credentials>) {
        dao.insertAll(credentialsList)
    }

    fun updateAll(credentialsList: List<Credentials>) {
        dao.updateAll(credentialsList)
    }


    fun getAll(): LiveData<List<Credentials>> = dao.getAll()

    fun getAllSync(): List<Credentials> = dao.getAllSync()

    fun getAllByUserId(userId: Long): LiveData<List<Credentials>> =
        dao.getAllByUserId(userId)

    fun getCredentialsUnderPositionByUserIdSync(
        position: Int,
        userId: Long = UserControl.userId
    ): List<Credentials> =
        dao.getCredentialsUnderPositionByUserIdSync(position, userId)
}
