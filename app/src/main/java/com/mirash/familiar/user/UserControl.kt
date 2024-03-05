package com.mirash.familiar.user

import android.util.Log
import com.mirash.familiar.db.Credentials
import com.mirash.familiar.db.RepositoryProvider
import com.mirash.familiar.db.RepositoryProvider.userRepository
import com.mirash.familiar.db.TAG_DB
import com.mirash.familiar.db.User
import com.mirash.familiar.db.logUserCredentialsSync
import com.mirash.familiar.eventmanager.EventManager
import com.mirash.familiar.eventmanager.event.DataEvent
import com.mirash.familiar.preferences.AppPreferences
import com.mirash.familiar.tool.AppEventType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

/**
 * @author Mirash
 */

const val TAG_USER = "USER"

object UserControl {
    var userId: Long = AppPreferences.getUserId()

    fun setUser(id: Long) {
        if (userId == id) {
            Log.d(TAG_USER, "setUser: user with id <$id> is already selected")
        } else {
            runBlocking {
                launch(Dispatchers.Default) {
                    userRepository.getByIdSync(id)?.let {
                        applyUserId(id)
                    }
                }
            }
        }
    }

    fun setRandomUser() {
        runBlocking {
            launch(Dispatchers.Default) {
                val users = userRepository.getAllSync()
                setUser(users[Random.nextInt(users.size)].id)
            }
        }
    }

    private fun applyUserId(id: Long) {
        Log.d(TAG_USER, "applyUserId: $id")
        userId = id
        AppPreferences.setUserId(id)
        EventManager.post(DataEvent(AppEventType.USER_SET, id, notifyToMainThread = true))
    }

    fun loadUser() {
        runBlocking {
            launch(Dispatchers.Default) {
                val user = userRepository.getByIdSync(userId)
                if (user == null) {
                    Log.d(TAG_USER, "user with id(${userId}) is null")
                    val users = userRepository.getAllSync()
                    if (users.isEmpty()) {
                        Log.d(TAG_USER, "users do not exist! create default user")
                        applyUserId(createUser())
                        createUser("A")
                        createUser("B")
                        logUserCredentialsSync()
                    } else {
                        Log.d(TAG_USER, "usersSize = ${users.size}, apply 0")
                        applyUserId(users[0].id)
                    }
                } else {
                    Log.d(TAG_USER, "user init good")
//                    val users = userRepository.getAllSync()
//                    for (value in users) {
//                        if (value.id != userId) {
//                            userRepository.getByIdSync(value.id)?.let {
//                                userRepository.delete(it)
//                                logUserCreds()
//                            }
//                            break
//                        }
//                    }
                }
            }
        }
    }

    private fun createUser(name: String = "User"): Long {
        val userId = userRepository.insert(User(name))
        Log.d(TAG_DB, "userId = $userId")
        RepositoryProvider.credentialsRepository.insertAll(getTestPredefinedCredentials(userId, name).also {
            Log.d(
                TAG_DB,
                "insert creds ${it.size}"
            )
        })
        return userId
    }

    fun getTestPredefinedCredentials(userId: Long, name: String): List<Credentials> {
        val list = ArrayList<Credentials>()
        val count = 5 * (1 + userId % 2)
        for (i in 0 until count) {
            val credentials = Credentials(userId)
            credentials.title = "${name}_title_$i"
            credentials.login = "${name}_login_$i"
            if (Random.nextBoolean()) {
                credentials.link = "${name}_link_$i"
            }
            if (Random.nextBoolean()) {
                credentials.details = "${name}_details_$i"
            }
            val value = Random.nextFloat()
            if (value > 0.7) {
                credentials.password = "${name}_password_$i"
                credentials.pin = "${name}_pin$i"
            } else {
                if (value > 0.35) {
                    credentials.password = "${name}_password_$i"
                } else {
                    credentials.pin = "${name}_pin$i"
                }
            }
            list.add(credentials)
        }
        return list
    }
}