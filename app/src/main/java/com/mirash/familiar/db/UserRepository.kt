package com.mirash.familiar.db

import androidx.lifecycle.LiveData
import com.mirash.familiar.user.UserControl

/**
 * @author Mirash
 */
class UserRepository(database: FamiliarDatabase) {
    private val dao: UserDao = database.getUserDao()

    fun getAll(): LiveData<List<User>> = dao.getAll()

    fun getAllSync(): List<User> = dao.getAllSync()

    fun getById(id: Long = UserControl.userId): LiveData<User> = dao.getById(id)

    fun getByIdSync(id: Long = UserControl.userId): User? = dao.getByIdSync(id)

    fun insert(user: User): Long = dao.insert(user)

    fun update(user: User) {
        dao.update(user)
    }

    fun delete(user: User) {
        dao.delete(user)
    }

    fun getUsersWithCredentialsSync(): List<UserWithCredentials> {
        return dao.getUsersWithCredentialsSync()
    }
}
