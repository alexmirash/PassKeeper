package com.mirash.familiar.tool.listener;

/**
 * @author Mirash
 */
public interface AppShowObserver {
    void onWentToBackground();

    void onWentToForeground();
}
