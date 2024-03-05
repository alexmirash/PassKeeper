package com.mirash.familiar.db

/**
 * @author Mirash
 */
class UserRepository(database: FamiliarDatabase) {
    private val dao: UserDao = database.getUserDao()

    fun insert(user: User): Long = dao.insert(user)

    fun getAllSync(): List<User> = dao.getAllSync()

    fun getByIdSync(id: Long): User? = dao.getByIdSync(id)

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
