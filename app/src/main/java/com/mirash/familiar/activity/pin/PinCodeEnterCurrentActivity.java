package com.mirash.familiar.activity.pin;

import android.os.Bundle;
import android.view.View;

import com.mirash.familiar.Const;
import com.mirash.familiar.R;
import com.mirash.familiar.preferences.EncryptedAppPreferences;

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
            startNewActivity(PinCodeCreateActivity.class);
        }
    }

    @Override
    public void onBackPressed() {
        startNewActivity(PinCodeEnterActivity.class);
    }
}