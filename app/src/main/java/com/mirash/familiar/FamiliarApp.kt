package com.mirash.familiar

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.mirash.familiar.tool.listener.AppShowObserver
import com.mirash.familiar.user.UserControl

/**
 * @author Mirash
 */
class FamiliarApp : Application() {
    private val appShowObservers = ArrayList<AppShowObserver>(1)
    override fun onCreate() {
        super.onCreate()
        instance = this
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            var numStarted = 0
            override fun onActivityStarted(activity: Activity) {
                if (numStarted == 0) {
                    for (listener in appShowObservers) {
                        listener.onWentToForeground()
                    }
                }
                numStarted++
            }

            override fun onActivityStopped(activity: Activity) {
                numStarted--
                if (numStarted == 0) {
                    for (listener in appShowObservers) {
                        listener.onWentToBackground()
                    }
                }
            }

            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}
            override fun onActivityCreated(activity: Activity, bundle: Bundle?) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
        UserControl.loadUser()
    }

    fun addAppShowObserver(observer: AppShowObserver) {
        appShowObservers.add(observer)
    }

    fun removeAppShowObserver(observer: AppShowObserver) {
        appShowObservers.remove(observer)
    }

    companion object {
        lateinit var instance: FamiliarApp
            private set
    }
}
