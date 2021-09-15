package com.mirash.passkeeper.activity.pin;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.mirash.passkeeper.Const;
import com.mirash.passkeeper.R;
import com.mirash.passkeeper.activity.main.MainActivity;
import com.mirash.passkeeper.db.RepositoryProvider;
import com.mirash.passkeeper.preferences.AppPreferences;
import com.mirash.passkeeper.preferences.EncryptedAppPreferences;

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
                showToast("Nuke!", Toast.LENGTH_LONG);
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

    private void showBiometricPrompt() {
        BiometricPrompt biometricPrompt = new BiometricPrompt(PinCodeEnterActivity.this,
                ContextCompat.getMainExecutor(this), new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                startNewActivity(MainActivity.class);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Cancel")
                .setAllowedAuthenticators(BIOMETRIC_STRONG)
                .setConfirmationRequired(false)
                .build();

        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.
        biometricPrompt.authenticate(promptInfo);
    }

    private void checkBio() {
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BIOMETRIC_STRONG)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.");
                inputView.initBiometricButton(view -> showBiometricPrompt());
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Log.e("MY_APP_TAG", "No biometric features available on this device.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Log.e("MY_APP_TAG", "Biometric features are currently unavailable.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                // Prompts the user to create credentials that your app accepts.
                final Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG | DEVICE_CREDENTIAL);
                startActivityForResult(enrollIntent, 15);
                break;
        }
    }
}