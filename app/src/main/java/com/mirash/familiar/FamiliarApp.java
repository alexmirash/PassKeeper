package com.mirash.familiar;

import android.app.Activity;
import android.app.Application;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mirash.familiar.tool.listener.AppShowObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mirash
 */

public class FamiliarApp extends Application {
    private static FamiliarApp instance;
    private final List<AppShowObserver> appShowObservers = new ArrayList<>(1);

    /**
     * @return the application instance
     */
    public static FamiliarApp getInstance() {
        return instance;
    }

    public static Resources getRes() {
        return instance.getResources();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            int numStarted = 0;

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                if (numStarted == 0) {
                    for (AppShowObserver listener : appShowObservers) {
                        listener.onWentToForeground();
                    }
                }
                numStarted++;
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                numStarted--;
                if (numStarted == 0) {
                    for (AppShowObserver listener : appShowObservers) {
                        listener.onWentToBackground();
                    }
                }
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
            }

            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
            }
        });
    }

    public void addAppShowObserver(AppShowObserver observer) {
        appShowObservers.add(observer);
    }

    public void removeAppShowObserver(AppShowObserver observer) {
        appShowObservers.remove(observer);
    }
}
