package com.mirash.familiar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.mirash.familiar.Const;
import com.mirash.familiar.activity.pin.PinCodeBaseActivity;
import com.mirash.familiar.activity.pin.PinCodeCreateActivity;
import com.mirash.familiar.activity.pin.PinCodeEnterActivity;
import com.mirash.familiar.preferences.EncryptedAppPreferences;

/**
 * @author Mirash
 */
public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Class<? extends PinCodeBaseActivity> activityClass;
        String pinCode = EncryptedAppPreferences.getInstance().getPinCode();
        if (pinCode == null || pinCode.length() != Const.PIN_CODE_SIZE) {
            activityClass = PinCodeCreateActivity.class;
        } else {
            activityClass = PinCodeEnterActivity.class;
        }
        startActivity(new Intent(this, activityClass));
        finish();
    }
}