package com.mirash.familiar.activity.pin

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.CallSuper
import com.mirash.familiar.R
import com.mirash.familiar.activity.main.MainActivity
import com.mirash.familiar.preferences.EncryptedAppPreferences
import com.mirash.familiar.tool.KEY_PIN_CODE
import com.mirash.familiar.tool.PIN_CODE_SIZE
import kotlin.math.abs
import kotlin.math.max

/**
 * @author Mirash
 */
class PinCodeConfirmActivity : PinCodeBaseActivity() {
    private var desiredPinCode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra(KEY_PIN_CODE).let {
            if (it == null || it.length != PIN_CODE_SIZE) {
                finishAffinity()
            }
            desiredPinCode = it
        }
        binding.pinButtonBottomStart.visibility = View.GONE
        binding.pinButtonBottomEnd.visibility = View.GONE
        binding.pinCodeDetails.let {
            it.setAlpha(0f)
            it.visibility = View.VISIBLE
            it.text = getString(R.string.pin_code_incorrect)
        }
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                startNewFinishCurrentActivity(PinCodeEnterActivity::class.java)
            }
        })
    }

    override fun getMessageRes(): Int = R.string.pin_code_confirm

    override fun checkPinCode(pinCode: String?) {
        if (pinCode == null || pinCode.length != PIN_CODE_SIZE) return
        if (desiredPinCode == pinCode) {
            EncryptedAppPreferences.pinCode = pinCode
            startNewFinishCurrentActivity(MainActivity::class.java)
        } else {
            changeAlpha(binding.pinCodeDetails, 1f)
        }
    }

    @CallSuper
    override fun handleEnteredPinCodeSizeChange(size: Int) {
        super.handleEnteredPinCodeSizeChange(size)
        if (size < PIN_CODE_SIZE) {
            changeAlpha(binding.pinCodeDetails, 0f)
        }
    }

    private fun changeAlpha(view: View, to: Float) {
        val from = view.alpha
        if (from == to) return
        val duration = max(1.0, (abs((to - from).toDouble()) * 250)).toInt()
        view.animate().setDuration(duration.toLong()).alpha(to).start()
    }
}