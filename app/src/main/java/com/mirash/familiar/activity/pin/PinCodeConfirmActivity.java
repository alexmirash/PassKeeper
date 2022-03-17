package com.mirash.familiar.activity.pin;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.CallSuper;

import com.mirash.familiar.Const;
import com.mirash.familiar.R;
import com.mirash.familiar.activity.main.MainActivity;
import com.mirash.familiar.preferences.EncryptedAppPreferences;
import com.mirash.familiar.tool.Utils;

import java.util.Objects;

/**
 * @author Mirash
 */
public class PinCodeConfirmActivity extends PinCodeBaseActivity {

    private String desiredPinCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        desiredPinCode = getIntent().getStringExtra(Const.KEY_PIN_CODE);
        if (desiredPinCode == null || desiredPinCode.length() != Const.PIN_CODE_SIZE) {
            finishAffinity();
        }
        buttonBottomStart.setVisibility(View.GONE);
        buttonBottomEnd.setVisibility(View.GONE);
        pinCodeDetailsView.setAlpha(0);
        pinCodeDetailsView.setVisibility(View.VISIBLE);
        pinCodeDetailsView.setText(getString(R.string.pin_code_incorrect));
    }

    @Override
    protected int getMessageRes() {
        return R.string.pin_code_confirm;
    }

    @Override
    protected void checkPinCode(String pinCode) {
        if (pinCode == null || pinCode.length() != Const.PIN_CODE_SIZE) return;
        if (Objects.equals(desiredPinCode, pinCode)) {
            EncryptedAppPreferences.getInstance().setPinCode(pinCode);
            startNewActivity(MainActivity.class);
        } else {
            changeAlpha(pinCodeDetailsView, 1);
        }
    }

    @CallSuper
    protected void handleEnteredPinCodeSizeChange(int size) {
        super.handleEnteredPinCodeSizeChange(size);
        if (size < Const.PIN_CODE_SIZE) {
            changeAlpha(pinCodeDetailsView, 0);
        }
    }

    private void changeAlpha(View view, float to) {
        float from = view.getAlpha();
        if (from == to) return;
        int duration = (int) Math.max(1, Math.abs(to - from) * 250);
        view.animate().setDuration(duration).alpha(to).start();
    }

    @Override
    public void onBackPressed() {
        if (Utils.isPinCodeActual(EncryptedAppPreferences.getInstance().getPinCode())) {
            startNewActivity(PinCodeEnterActivity.class);
        } else {
            onBackPressed();
        }
    }
}