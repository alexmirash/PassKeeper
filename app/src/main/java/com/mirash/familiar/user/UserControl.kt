package com.mirash.familiar.user

import android.util.Log
import com.mirash.familiar.db.Credentials
import com.mirash.familiar.db.RepositoryProvider
import com.mirash.familiar.db.RepositoryProvider.userRepository
import com.mirash.familiar.db.TAG_DB
import com.mirash.familiar.db.User
import com.mirash.familiar.db.logUserCreds
import com.mirash.familiar.preferences.AppPreferences
import java.util.Random
import java.util.concurrent.Executors

/**
 * @author Mirash
 */

const val TAG_USER = "USER"

object UserControl {
    var userId: Long = AppPreferences.getUserId()

    private fun applyUserId(id: Long) {
        Log.d(TAG_USER, "applyUserId: $id")
        userId = id
        AppPreferences.setUserId(id)
    }

    fun loadUser() {
        Executors.newSingleThreadScheduledExecutor().execute {
            val user = userRepository.getByIdSync(userId)
            if (user == null) {
                Log.d(TAG_USER, "user with id(${userId}) is null")
                val users = userRepository.getAllSync()
                if (users.isEmpty()) {
                    Log.d(TAG_USER, "users do not exist! create default user")
                    applyUserId(createUser())
                    createUser("User_1")
                    createUser("User_2")
                } else {
                    Log.d(TAG_USER, "usersSize = ${users.size}, apply 0")
                    applyUserId(users[0].id)
                }
            } else {
                Log.d(TAG_USER, "user init good")
                val users = userRepository.getAllSync()
                for (value in users) {
                    if (value.id != userId) {
                        userRepository.getByIdSync(value.id)?.let {
                            userRepository.delete(it)
                            logUserCreds()
                        }
                        return@execute
                    }
                }
            }
        }
    }

    private fun createUser(name: String = "User"): Long {
        val userId = userRepository.insert(User(name))
        Log.d(TAG_DB, "userId = $userId")
        RepositoryProvider.credentialsRepository.insertAll(getTestPredefinedCredentials(userId))
        return userId
    }

    fun getTestPredefinedCredentials(userId: Long): List<Credentials> {
        val list = ArrayList<Credentials>()
        val r = Random()
        val count = 5 * (1 + userId % 2)
        for (i in 0 until count) {
            val credentials = Credentials(userId)
            credentials.title = "Test_title_$i"
            credentials.login = "test_login_$i"
            if (r.nextBoolean()) {
                credentials.link = "test_link_$i"
            }
            if (r.nextBoolean()) {
                credentials.details = "test_details_$i"
            }
            val value = r.nextFloat()
            if (value > 0.7) {
                credentials.password = "test_password_$i"
                credentials.pin = "text_pin$i"
            } else {
                if (value > 0.35) {
                    credentials.password = "test_password_$i"
                } else {
                    credentials.pin = "text_pin$i"
                }
            }
            list.add(credentials)
        }
        return list
    }
}