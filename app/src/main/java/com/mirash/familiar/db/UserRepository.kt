package com.mirash.familiar.db

import androidx.lifecycle.LiveData

/**
 * @author Mirash
 */
class UserRepository(database: FamiliarDatabase) : IRepository<User> {
    private val dao: UserDao = database.getUserDao()

    fun getAll(): LiveData<List<User>> = dao.getAll()

    fun getAllSync(): List<User> = dao.getAllSync()
    override fun deleteById(id: Long) {
        dao.deleteById(id)
    }

    override fun getById(id: Long): LiveData<User> = dao.getById(id)

    override fun getByIdSync(id: Long): User? = dao.getByIdSync(id)

    override fun insert(entity: User): Long = dao.insert(entity)

    override fun update(entity: User) {
        dao.update(entity)
    }

    override fun delete(entity: User) {
        dao.deleteById(entity)
    }

    fun getUsersWithCredentialsSync(): List<UserWithCredentials> {
        return dao.getUsersWithCredentialsSync()
    }
}
