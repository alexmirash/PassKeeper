package com.mirash.familiar.activity.pin

import android.os.Bundle
import android.os.VibrationEffect
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import com.mirash.familiar.R
import com.mirash.familiar.activity.main.MainActivity
import com.mirash.familiar.db.RepositoryProvider.nuke
import com.mirash.familiar.preferences.AppPreferences
import com.mirash.familiar.preferences.EncryptedAppPreferences
import com.mirash.familiar.tool.INVALID_INPUT_NUKE_COUNT
import com.mirash.familiar.tool.PIN_CODE_SIZE
import com.mirash.familiar.tool.getSystemVibrator

/**
 * @author Mirash
 */
class PinCodeEnterActivity : PinCodeBaseActivity() {
    private var toast: Toast? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.pinButtonBottomStart.visibility = View.VISIBLE
        binding.pinButtonBottomEnd.visibility = View.GONE
        binding.pinButtonBottomStart.text = getString(R.string.pin_code_change)
        binding.pinButtonBottomStart.setOnClickListener { _: View ->
            startNewFinishCurrentActivity(PinCodeEnterCurrentActivity::class.java)
        }
        checkBio()
    }

    override fun getMessageRes(): Int = R.string.pin_code_enter

    override fun checkPinCode(pinCode: String?) {
        if (pinCode == null || pinCode.length != PIN_CODE_SIZE) return
        val actualPinCode = EncryptedAppPreferences.pinCode
        if (actualPinCode == pinCode) {
            AppPreferences.removeInvalidInputsCount()
            startNewFinishCurrentActivity(MainActivity::class.java)
        } else {
            getSystemVibrator(this)?.vibrate(
                VibrationEffect.createOneShot(
                    150, VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
            val invalidInputsCount = AppPreferences.getInvalidInputsCount()
            val attemptsLeft = INVALID_INPUT_NUKE_COUNT - 1 - invalidInputsCount
            if (attemptsLeft == 0) {
                AppPreferences.removeInvalidInputsCount()
                nuke()
                showToast(getString(R.string.nuke), Toast.LENGTH_LONG)
            } else {
                AppPreferences.setInvalidInputsCount(invalidInputsCount + 1)
                if (attemptsLeft <= 3) {
                    showToast(attemptsLeft.toString(), Toast.LENGTH_SHORT)
                }
            }
        }
    }

    private fun showToast(message: String, duration: Int) {
        toast?.cancel()
        toast = Toast.makeText(this, message, duration).apply { show() }
    }

    private fun checkBio() {
        when (BiometricManager.from(this).canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Log.d(TAG_PIN, "App can authenticate using biometrics.")
                binding.pinInput.initBiometricButton { _ -> showBiometricPrompt() }
                showBiometricPrompt()
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> Log.e(
                TAG_PIN, "No biometric features available on this device."
            )

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> Log.e(
                TAG_PIN, "Biometric features are currently unavailable."
            )

            else -> {}
        }
    }

    private fun showBiometricPrompt() {
        val biometricPrompt = BiometricPrompt(this,
            ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    showToast(String.format(getString(R.string.auth_error), errString), Toast.LENGTH_SHORT)
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    AppPreferences.removeInvalidInputsCount()
                    startNewFinishCurrentActivity(MainActivity::class.java)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    showToast(getString(R.string.auth_fail), Toast.LENGTH_SHORT)
                }
            })
        val promptInfo = PromptInfo.Builder().setTitle(getString(R.string.bio_login_title))
            .setSubtitle(getString(R.string.bio_login_message)).setNegativeButtonText(getString(R.string.cancel))
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .setConfirmationRequired(false).build()
        biometricPrompt.authenticate(promptInfo)
    }
}