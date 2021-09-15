package com.mirash.familiar.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author Mirash
 */

public abstract class BasePreferences {
    protected SharedPreferences preferences;

    protected abstract SharedPreferences initPreferences(Context context);

    public BasePreferences(Context context) {
        preferences = initPreferences(context);
    }

    public void putBool(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    public boolean getBool(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    public void putInt(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    public int getInt(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public void putLong(String key, long value) {
        preferences.edit().putLong(key, value).apply();
    }

    public long getLong(String key, long defValue) {
        return preferences.getLong(key, defValue);
    }

    public void putFloat(String key, float value) {
        preferences.edit().putFloat(key, value).apply();
    }

    public float getFloat(String key, float defValue) {
        return preferences.getFloat(key, defValue);
    }

    public String getString(String key, String value) {
        return preferences.getString(key, value);
    }

    public void putString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    public boolean contains(String key) {
        return preferences.contains(key);
    }

    public void remove(String key) {
        preferences.edit().remove(key).apply();
    }

    public SharedPreferences.Editor edit() {
        return preferences.edit();
    }

    public void putIntArray(String key, int[] array) {
        if (array == null) {
            remove(key);
            return;
        }
        Gson gson = new Gson();
        String str = gson.toJson(array);
        putString(key, str);
    }

    public int[] getIntArray(String key) {
        String str = getString(key, null);
        if (str == null) {
            return null;
        }
        return new Gson().fromJson(str, new TypeToken<int[]>() {
        }.getType());
    }

    public <T> void putObject(String key, T object) {
        if (object == null) {
            remove(key);
            return;
        }
        Gson gson = new Gson();
        String str = gson.toJson(object);
        putString(key, str);
    }

    public <T> T getObject(String key) {
        String str = getString(key, null);
        if (str == null) {
            return null;
        }
        return new Gson().fromJson(str, new TypeToken<T>() {
        }.getType());
    }
}
