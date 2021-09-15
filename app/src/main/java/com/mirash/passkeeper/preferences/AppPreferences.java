package com.mirash.passkeeper.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.mirash.passkeeper.Const;
import com.mirash.passkeeper.PassKeeperApp;

/**
 * @author Mirash
 */

public class AppPreferences extends BasePreferences {
    private static AppPreferences instance;

    public AppPreferences(Context context) {
        super(context);
    }

    @Override
    protected SharedPreferences initPreferences(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static AppPreferences getInstance() {
        if (instance == null) {
            instance = new AppPreferences(PassKeeperApp.getInstance().getApplicationContext());
        }
        return instance;
    }

    public int getInvalidInputsCount() {
        return getInt(Const.KEY_INVALID_INPUT_COUNT, 0);
    }

    public void setInvalidInputsCount(int value) {
        putInt(Const.KEY_INVALID_INPUT_COUNT, value);
    }

    public void removeInvalidInputsCount() {
        remove(Const.KEY_INVALID_INPUT_COUNT);
    }
}
