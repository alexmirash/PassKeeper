package com.mirash.familiar.activity.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mirash.familiar.db.Credentials
import com.mirash.familiar.db.RepositoryProvider
import com.mirash.familiar.db.User
import com.mirash.familiar.eventmanager.EventManager
import com.mirash.familiar.eventmanager.event.DataEvent
import com.mirash.familiar.eventmanager.subscribe
import com.mirash.familiar.eventmanager.subscription.Subscription
import com.mirash.familiar.model.credentials.CredentialsItem
import com.mirash.familiar.tool.AppEventType
import com.mirash.familiar.user.TAG_USER
import com.mirash.familiar.user.UserControl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * @author Mirash
 */
class MainActivityModel(application: Application, private var callback: MainModelCallback? = null) :
    AndroidViewModel(application) {

    private var userLiveData: LiveData<User> = RepositoryProvider.userRepository.getById(UserControl.userId)
    private var credentialsLiveData: LiveData<List<Credentials>> =
        RepositoryProvider.credentialsRepository.getAllByUserId(UserControl.userId)

    init {
        callback?.setUserObservers(userLiveData, credentialsLiveData)
    }

    val usersLiveData: LiveData<List<User>> = RepositoryProvider.userRepository.getAll()


    private val userSubscription: Subscription = EventManager.subscribe<DataEvent<Long>>(AppEventType.USER_SET) {
        Log.d(TAG_USER, "MainActivityModel USER_SET event: ${it.data}")
        callback?.clearUserObservers(userLiveData, credentialsLiveData)
        userLiveData = RepositoryProvider.userRepository.getById(it.data)
        credentialsLiveData = RepositoryProvider.credentialsRepository.getAllByUserId(it.data)
        callback?.setUserObservers(userLiveData, credentialsLiveData)
    }

    fun handleOrderChanged(items: List<CredentialsItem>) {
        runBlocking { launch(Dispatchers.Default) { handleOrderChangedSync(items) } }
    }

    private fun handleOrderChangedSync(items: List<CredentialsItem>) {
        val credentials: MutableList<Credentials> = ArrayList()
        for (i in items.indices) {
            val id = items[i].id
            RepositoryProvider.credentialsRepository.getByIdSync(id)?.let {
                it.position = i
                credentials.add(it)
            }
        }
        RepositoryProvider.credentialsRepository.updateAll(credentials)
    }

    override fun onCleared() {
        super.onCleared()
        callback = null
        userSubscription.unsubscribe()
    }
}
