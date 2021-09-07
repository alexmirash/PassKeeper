package com.mirash.passkeeper.activity.pin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mirash.passkeeper.Const;
import com.mirash.passkeeper.R;
import com.mirash.passkeeper.preferences.EncryptedAppPreferences;

import java.util.Objects;

/**
 * @author Mirash
 */
public class PinCodeEnterCurrentActivity extends PinCodeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buttonBottomStart.setVisibility(View.VISIBLE);
        buttonBottomEnd.setVisibility(View.GONE);
        buttonBottomStart.setText(getString(R.string.cancel));
        buttonBottomStart.setOnClickListener(view -> onBackPressed());
    }

    @Override
    protected int getMessageRes() {
        return R.string.pin_code_enter_current;
    }

    @Override
    protected void checkPinCode(String pinCode) {
        if (pinCode == null || pinCode.length() != Const.PIN_CODE_SIZE) return;
        String actualPinCode = EncryptedAppPreferences.getInstance().getPinCode();
        if (Objects.equals(actualPinCode, pinCode)) {
            EncryptedAppPreferences.getInstance().setPinCode(null);
            finish();
            Intent intent = new Intent(PinCodeEnterCurrentActivity.this, PinCodeCreateActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(PinCodeEnterCurrentActivity.this, PinCodeEnterActivity.class);
        startActivity(intent);
    }
}