package com.mirash.passkeeper;

import android.app.Application;
import android.content.res.Resources;

/**
 * @author Mirash
 */

public class PassKeeperApp extends Application {
    private static PassKeeperApp instance;

    /**
     * @return the application instance
     */
    public static PassKeeperApp getInstance() {
        return instance;
    }

    public static Resources getRes() {
        return instance.getResources();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
