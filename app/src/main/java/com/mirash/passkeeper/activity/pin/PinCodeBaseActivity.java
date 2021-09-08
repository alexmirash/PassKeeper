package com.mirash.passkeeper.activity.pin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.CallSuper;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.mirash.passkeeper.R;
import com.mirash.passkeeper.activity.pin.view.PinIndicatorView;
import com.mirash.passkeeper.view.PinInputView;

/**
 * @author Mirash
 */
public abstract class PinCodeBaseActivity extends AppCompatActivity {

    protected PinIndicatorView indicatorView;
    protected TextView pinCodeMessageView;
    protected TextView pinCodeDetailsView;
    protected TextView buttonBottomStart;
    protected TextView buttonBottomEnd;
    protected PinActivityModel viewModel;
    protected PinInputView inputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        pinCodeMessageView = findViewById(R.id.pin_code_message);
        pinCodeDetailsView = findViewById(R.id.pin_code_details);
        indicatorView = findViewById(R.id.pin_indicator);
        buttonBottomStart = findViewById(R.id.pin_button_bottom_start);
        buttonBottomEnd = findViewById(R.id.pin_button_bottom_end);
        inputView = findViewById(R.id.pin_input);

        viewModel = new ViewModelProvider(this).get(PinActivityModel.class);
        viewModel.getPinCodeSizeLiveData().observe(this, this::handleEnteredPinCodeSizeChange);
        viewModel.getPinCodeCheckLiveData().observe(this, this::checkPinCode);
        inputView.setCallback(viewModel);

        pinCodeMessageView.setText(getString(getMessageRes()));
    }

    @CallSuper
    protected void handleEnteredPinCodeSizeChange(int size) {
        indicatorView.setCheckedSize(size);
    }

    @StringRes
    protected abstract int getMessageRes();

    protected abstract void checkPinCode(String pinCode);

    protected void startNewActivity(Class<?> activityClass) {
        finish();
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
//        overridePendingTransition(0 ,0);
    }
}