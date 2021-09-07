package com.mirash.passkeeper.tool.listener;

/**
 * @author Mirash
 */
public interface AppShowObserver {
    void onWentToBackground();

    void onWentToForeground();
}
