package com.mirash.familiar.activity.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mirash.familiar.db.Credentials
import com.mirash.familiar.db.RepositoryProvider
import com.mirash.familiar.model.CredentialsItem
import java.util.concurrent.Executors

/**
 * @author Mirash
 */
class MainActivityModel(application: Application) : AndroidViewModel(application) {
    val credentialsModelLiveData: LiveData<List<Credentials>> =
        RepositoryProvider.credentialsRepository.getAllCredentialsByUserId()

    fun handleOrderChanged(items: List<CredentialsItem>) {
        Executors.newSingleThreadScheduledExecutor().execute { handleOrderChangedSync(items) }
    }

    private fun handleOrderChangedSync(items: List<CredentialsItem>) {
        val credentials: MutableList<Credentials> = ArrayList()
        for (i in items.indices) {
            val id = items[i].id
            val credById = RepositoryProvider.credentialsRepository.getCredentialsByIdSync(id)
            credById.position = i
            credentials.add(credById)
        }
        RepositoryProvider.credentialsRepository.updateAll(credentials)
    }
}
