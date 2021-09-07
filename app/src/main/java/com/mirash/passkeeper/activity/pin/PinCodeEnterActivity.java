package com.mirash.passkeeper.activity.pin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mirash.passkeeper.Const;
import com.mirash.passkeeper.R;
import com.mirash.passkeeper.activity.main.MainActivity;
import com.mirash.passkeeper.preferences.EncryptedAppPreferences;

import java.util.Objects;

/**
 * @author Mirash
 */
public class PinCodeEnterActivity extends PinCodeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buttonBottomStart.setVisibility(View.VISIBLE);
        buttonBottomEnd.setVisibility(View.GONE);
        buttonBottomStart.setText(getString(R.string.pin_code_change));
        buttonBottomStart.setOnClickListener(view -> {
            finish();
            Intent intent = new Intent(PinCodeEnterActivity.this, PinCodeEnterCurrentActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected int getMessageRes() {
        return R.string.pin_code_enter;
    }

    @Override
    protected void checkPinCode(String pinCode) {
        if (pinCode == null || pinCode.length() != Const.PIN_CODE_SIZE) return;
        String actualPinCode = EncryptedAppPreferences.getInstance().getPinCode();
        if (Objects.equals(actualPinCode, pinCode)) {
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}