package com.mirash.familiar.activity.pin

import android.content.Intent
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mirash.familiar.databinding.ActivityPinBinding

/**
 * @author Mirash
 */

abstract class PinCodeBaseActivity : AppCompatActivity() {
    protected lateinit var binding: ActivityPinBinding
    private lateinit var model: PinActivityModel

    @StringRes
    protected abstract fun getMessageRes(): Int
    protected abstract fun checkPinCode(pinCode: String?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        model = ViewModelProvider(this)[PinActivityModel::class.java]
        model.pinCodeSizeLiveData.observe(this) { size: Int -> handleEnteredPinCodeSizeChange(size) }
        model.pinCodeCheckLiveData.observe(this) { pinCode: String? -> checkPinCode(pinCode) }
        binding.pinInput.setCallback(model)
        binding.pinCodeMessage.text = getString(getMessageRes())
    }

    @CallSuper
    protected open fun handleEnteredPinCodeSizeChange(size: Int) {
        binding.pinIndicator.setCheckedSize(size)
    }

    protected fun startNewActivity(activityClass: Class<*>?) {
        finish()
        startActivity(Intent(this, activityClass))
    }
}

const val TAG_PIN = "PIN"