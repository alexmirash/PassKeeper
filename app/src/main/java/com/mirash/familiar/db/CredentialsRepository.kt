package com.mirash.familiar.db

import androidx.lifecycle.LiveData
import com.mirash.familiar.user.UserControl

/**
 * @author Mirash
 */
class CredentialsRepository(database: FamiliarDatabase) {
    private val dao: CredentialsDao = database.getCredentialsDao()

    fun insertAll(credentialsList: List<Credentials>) {
        dao.insertAll(credentialsList)
    }

    fun updateAll(credentialsList: List<Credentials>) {
        dao.updateAll(credentialsList)
    }

    fun insert(credentials: Credentials) {
        dao.insert(credentials)
    }

    fun update(credentials: Credentials) {
        dao.update(credentials)
    }

    fun getAllCredentials(): LiveData<List<Credentials>> = dao.getAll()

    fun getAllCredentialsSync(): List<Credentials> = dao.getAllSync()

    fun getAllCredentialsByUserId(userId: Long = UserControl.userId): LiveData<List<Credentials>> =
        dao.getAllByUserId(userId)

    fun getCredentialsById(id: Long): LiveData<Credentials> = dao.getById(id)

    fun getCredentialsByIdSync(id: Long): Credentials = dao.getByIdSync(id)

    fun getCredentialsUnderPositionByUserIdSync(
        position: Int,
        userId: Long = UserControl.userId
    ): List<Credentials> =
        dao.getCredentialsUnderPositionByUserIdSync(position, userId)

    fun deleteCredentialsById(id: Long) {
        dao.deleteById(id)
    }
}
