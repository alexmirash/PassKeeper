package com.mirash.familiar.activity.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.ref.WeakReference

/**
 * @author Mirash
 */
class MainViewModelFactory(
    private val application: Application,
    callback: MainModelCallback
) : ViewModelProvider.NewInstanceFactory() {
    private val callbackRef: WeakReference<MainModelCallback> = WeakReference(callback)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityModel(application, callbackRef.get()) as T
    }
}