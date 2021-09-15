package com.mirash.familiar.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.mirash.familiar.Const;
import com.mirash.familiar.FamiliarApp;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * @author Mirash
 */

public class EncryptedAppPreferences extends BasePreferences {
    private static EncryptedAppPreferences instance;

    public EncryptedAppPreferences(Context context) {
        super(context);
    }

    @Override
    protected SharedPreferences initPreferences(Context context) {
        SharedPreferences sharedPreferences;
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPreferences = EncryptedSharedPreferences.create(
                    "pass_keeper_prefs",
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            Log.e("Prefs", e.toString());
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public static EncryptedAppPreferences getInstance() {
        if (instance == null) {
            instance = new EncryptedAppPreferences(FamiliarApp.getInstance().getApplicationContext());
        }
        return instance;
    }

    public void setPinCode(String value) {
        putString(Const.KEY_PIN_CODE, value);
    }

    public String getPinCode() {
        return getString(Const.KEY_PIN_CODE, null);
    }
}
