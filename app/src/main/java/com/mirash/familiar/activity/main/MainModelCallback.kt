package com.mirash.familiar.activity.main

import androidx.lifecycle.LiveData
import com.mirash.familiar.db.Credentials
import com.mirash.familiar.db.User

/**
 * @author Mirash
 */
interface MainModelCallback {
    fun setUserObservers(user: LiveData<User>, credentials: LiveData<List<Credentials>>)
    fun clearUserObservers(user: LiveData<User>, credentials: LiveData<List<Credentials>>)
}