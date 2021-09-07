package com.mirash.passkeeper.activity.pin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mirash.passkeeper.Const;
import com.mirash.passkeeper.R;
import com.mirash.passkeeper.activity.main.MainActivity;
import com.mirash.passkeeper.preferences.EncryptedAppPreferences;
import com.mirash.passkeeper.tool.Utils;

import java.util.Objects;

/**
 * @author Mirash
 */
public class PinCodeConfirmActivity extends PinCodeBaseActivity {

    private String desiredPinCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buttonBottomStart.setVisibility(View.GONE);
        buttonBottomEnd.setVisibility(View.GONE);
        desiredPinCode = getIntent().getStringExtra(Const.KEY_PIN_CODE);
        if (desiredPinCode == null || desiredPinCode.length() != Const.PIN_CODE_SIZE)
            finishAffinity();
    }

    @Override
    protected int getMessageRes() {
        return R.string.pin_code_confirm;
    }

    @Override
    protected void checkPinCode(String pinCode) {
        if (pinCode == null || pinCode.length() != Const.PIN_CODE_SIZE) return;
        if (Objects.equals(desiredPinCode, pinCode)) {
            EncryptedAppPreferences.getInstance().putString(Const.KEY_PIN_CODE, pinCode);
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (Utils.isPinCodeActual(EncryptedAppPreferences.getInstance().getPinCode())) {
            finish();
            Intent intent = new Intent(PinCodeConfirmActivity.this, PinCodeEnterActivity.class);
            startActivity(intent);
        } else {
            onBackPressed();
        }
    }
}