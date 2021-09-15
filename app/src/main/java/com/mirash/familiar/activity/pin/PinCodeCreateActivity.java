package com.mirash.familiar.activity.pin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mirash.familiar.Const;
import com.mirash.familiar.R;
import com.mirash.familiar.preferences.EncryptedAppPreferences;
import com.mirash.familiar.tool.Utils;

/**
 * @author Mirash
 */
public class PinCodeCreateActivity extends PinCodeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buttonBottomStart.setVisibility(View.GONE);
        buttonBottomEnd.setVisibility(View.VISIBLE);
        buttonBottomEnd.setText(getString(R.string.done));
    }

    @Override
    protected int getMessageRes() {
        return R.string.pin_code_create;
    }

    @Override
    protected void handleEnteredPinCodeSizeChange(int size) {
        super.handleEnteredPinCodeSizeChange(size);
        if (size == Const.PIN_CODE_SIZE) {
            buttonBottomEnd.setEnabled(true);
        } else {
            buttonBottomEnd.setEnabled(false);
            buttonBottomEnd.setOnClickListener(null);
        }
    }

    @Override
    protected void checkPinCode(String pinCode) {
        buttonBottomEnd.setOnClickListener(view -> {
            finish();
            Intent intent = new Intent(PinCodeCreateActivity.this, PinCodeConfirmActivity.class);
            intent.putExtra(Const.KEY_PIN_CODE, pinCode);
            startActivity(intent);
        });
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