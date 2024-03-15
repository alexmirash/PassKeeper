package com.mirash.familiar.activity.edit.credentials

import android.app.Application
import com.mirash.familiar.activity.edit.base.BaseEditViewModel
import com.mirash.familiar.db.Credentials
import com.mirash.familiar.db.CredentialsRepository
import com.mirash.familiar.db.RepositoryProvider.credentialsRepository
import com.mirash.familiar.model.credentials.ICredentials

/**
 * @author Mirash
 */
class CredentialsEditViewModel(application: Application) :
    BaseEditViewModel<ICredentials, Credentials>(application) {

    companion object {
        const val INDEX_TITLE = 0
        const val INDEX_LINK = 1
        const val INDEX_LOGIN = 2
        const val INDEX_EMAIL = 3
        const val INDEX_PHONE = 4
        const val INDEX_PASSWORD = 5
        const val INDEX_PIN = 6
    }

    private val fillStates = booleanArrayOf(false, false, false, false, false, false, false)
    var credentialsPosition = 0

    override val repository: CredentialsRepository
        get() = credentialsRepository

    override fun createEntity(): Credentials = Credentials()

    override fun deleteSync(id: Long) {
        super.deleteSync(id)
        val credentials =
            repository.getCredentialsUnderPositionByUserIdSync(credentialsPosition)
        for (item in credentials) {
            item.position--
        }
        repository.updateAll(credentials)
    }

    override fun fill(from: ICredentials, to: Credentials) {
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

    override fun setFillState(state: Boolean, index: Int) {
        fillStates[index] = state
        val result = ((fillStates[INDEX_TITLE] || fillStates[INDEX_LINK])
                && (fillStates[INDEX_LOGIN] || fillStates[INDEX_EMAIL] || fillStates[INDEX_PHONE])
                && (fillStates[INDEX_PASSWORD] || fillStates[INDEX_PIN]))
        super.setFillState(result, index)
    }
}
