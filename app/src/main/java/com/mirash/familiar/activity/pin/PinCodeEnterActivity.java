package com.mirash.familiar.activity.pin;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;

import android.content.Context;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.mirash.familiar.Const;
import com.mirash.familiar.R;
import com.mirash.familiar.activity.main.MainActivity;
import com.mirash.familiar.db.RepositoryProvider;
import com.mirash.familiar.preferences.AppPreferences;
import com.mirash.familiar.preferences.EncryptedAppPreferences;

import java.util.Objects;

/**
 * @author Mirash
 */
public class PinCodeEnterActivity extends PinCodeBaseActivity {
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buttonBottomStart.setVisibility(View.VISIBLE);
        buttonBottomEnd.setVisibility(View.GONE);
        buttonBottomStart.setText(getString(R.string.pin_code_change));
        buttonBottomStart.setOnClickListener(view -> startNewActivity(PinCodeEnterCurrentActivity.class));
        checkBio();
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
            AppPreferences.getInstance().removeInvalidInputsCount();
            startNewActivity(MainActivity.class);
        } else {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
            int invalidInputsCount = AppPreferences.getInstance().getInvalidInputsCount();
            int attemptsLeft = Const.INVALID_INPUT_NUKE_COUNT - 1 - invalidInputsCount;
            if (attemptsLeft == 0) {
                AppPreferences.getInstance().removeInvalidInputsCount();
                RepositoryProvider.getCredentialsRepository().nuke();
                showToast(getString(R.string.nuke), Toast.LENGTH_LONG);
            } else {
                AppPreferences.getInstance().setInvalidInputsCount(invalidInputsCount + 1);
                if (attemptsLeft <= 3) {
                    showToast(String.valueOf(attemptsLeft), Toast.LENGTH_SHORT);
                }
            }
        }
    }

    private void showToast(String message, int duration) {
        if (toast != null) toast.cancel();
        toast = Toast.makeText(this, message, duration);
        toast.show();
    }

    private void checkBio() {
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BIOMETRIC_STRONG)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d(TAG, "App can authenticate using biometrics.");
                inputView.initBiometricButton(view -> showBiometricPrompt());
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Log.e(TAG, "No biometric features available on this device.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Log.e(TAG, "Biometric features are currently unavailable.");
                break;
        }
    }

    private void showBiometricPrompt() {
        BiometricPrompt biometricPrompt = new BiometricPrompt(PinCodeEnterActivity.this,
                ContextCompat.getMainExecutor(this), new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                showToast(String.format(getString(R.string.auth_error), errString), Toast.LENGTH_SHORT);
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                startNewActivity(MainActivity.class);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                showToast(getString(R.string.auth_fail), Toast.LENGTH_SHORT);
            }
        });
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.bio_login_title))
                .setSubtitle(getString(R.string.bio_login_message))
                .setNegativeButtonText(getString(R.string.cancel))
                .setAllowedAuthenticators(BIOMETRIC_STRONG)
                .setConfirmationRequired(false)
                .build();
        biometricPrompt.authenticate(promptInfo);
    }
}