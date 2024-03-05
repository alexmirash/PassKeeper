package com.mirash.familiar.activity.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mirash.familiar.db.Credentials
import com.mirash.familiar.db.RepositoryProvider.credentialsRepository
import com.mirash.familiar.model.CredentialsModel
import com.mirash.familiar.model.ICredentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * @author Mirash
 */
class CredentialsEditViewModel(application: Application) : AndroidViewModel(application) {
    var credentialsLiveData: LiveData<Credentials>? = null
        private set
    val saveButtonEnableStateLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    private val fillStates = booleanArrayOf(false, false, false, false, false, false, false)
    var credentialsId: Long? = null
        private set
    var credentialsPosition = 0

    fun setCredentialsId(id: Long) {
        credentialsId = id
        credentialsLiveData = credentialsRepository.getCredentialsById(id)
    }

    fun removeCredentialsObserver(observer: Observer<Credentials>) {
        credentialsLiveData?.removeObserver(observer)
    }

    fun saveCredentials(credentials: ICredentials) {
        runBlocking { launch(Dispatchers.Default) { insertCredentialsSync(credentials) } }
    }

    private fun insertCredentialsSync(data: ICredentials) {
        credentialsLiveData?.value?.let {
            fillCredentials(data, it)
            credentialsRepository.update(it)
            return
        }
        val credentials = Credentials()
        fillCredentials(data, credentials)
        credentialsRepository.insert(credentials)
    }

    fun deleteCredentials() {
        runBlocking {
            launch(Dispatchers.Default) {
                credentialsId?.let {
                    credentialsRepository.deleteCredentialsById(it)
                    val credentials =
                        credentialsRepository.getCredentialsUnderPositionByUserIdSync(credentialsPosition)
                    for (c in credentials) {
                        c.position--
                    }
                    credentialsRepository.updateAll(credentials)
                }
            }
        }
    }

    fun setFillState(index: Int, state: Boolean) {
        fillStates[index] = state
        val result = ((fillStates[INDEX_TITLE] || fillStates[INDEX_LINK])
                && (fillStates[INDEX_LOGIN] || fillStates[INDEX_EMAIL] || fillStates[INDEX_PHONE])
                && (fillStates[INDEX_PASSWORD] || fillStates[INDEX_PIN]))
        saveButtonEnableStateLiveData.value = result
    }

    companion object {
        const val INDEX_TITLE = 0
        const val INDEX_LINK = 1
        const val INDEX_LOGIN = 2
        const val INDEX_EMAIL = 3
        const val INDEX_PHONE = 4
        const val INDEX_PASSWORD = 5
        const val INDEX_PIN = 6
        private fun fillCredentials(from: ICredentials, to: CredentialsModel) {
            to.title = from.title
            to.link = from.link
            to.login = from.login
            to.email = from.email
            to.phone = from.phone
            to.password = from.password
            to.pin = from.pin
            to.details = from.details
            to.position = from.position
        }
    }
}
