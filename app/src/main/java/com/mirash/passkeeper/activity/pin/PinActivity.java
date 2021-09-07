package com.mirash.passkeeper.activity.pin;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.mirash.passkeeper.R;
import com.mirash.passkeeper.activity.main.MainActivity;
import com.mirash.passkeeper.view.PinInputView;

/**
 * @author Mirash
 */
public class PinActivity extends AppCompatActivity {

    private PinInputView inputView;
    private PinIndicatorView indicatorView;
    private PinActivityModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        indicatorView = findViewById(R.id.pin_indicator);
        inputView = findViewById(R.id.pin_input);
        viewModel = new ViewModelProvider(this).get(PinActivityModel.class);
        viewModel.getPinCodeSizeLiveData().observe(this, size -> indicatorView.setCheckedSize(size));
        viewModel.getPinCodeCorrectLiveData().observe(this, accessGranted -> {
            if (accessGranted != null && accessGranted) {
                showPassActivity();
            }
        });
        inputView.setCallback(viewModel);
    }

    private void showPassActivity() {
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}