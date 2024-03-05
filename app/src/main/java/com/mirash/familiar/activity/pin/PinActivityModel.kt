package com.mirash.familiar.activity.pin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mirash.familiar.tool.PIN_CODE_SIZE
import com.mirash.familiar.view.PinInputView
import java.util.Stack

/**
 * @author Mirash
 */
class PinActivityModel(application: Application) : AndroidViewModel(application), PinInputView.PinCallback {
    val pinCodeSizeLiveData: MutableLiveData<Int> = MutableLiveData<Int>(0)
    val pinCodeCheckLiveData: MutableLiveData<String> = MutableLiveData<String>(null)
    private val pinCode: Stack<Int> = Stack()

    override fun onDigitClick(digit: Int) {
        if (pinCode.size >= PIN_CODE_SIZE) return
        pinCode.push(digit)
        val size = pinCode.size
        pinCodeSizeLiveData.value = size
        if (size == PIN_CODE_SIZE) {
            checkPinCode()
        }
    }

    override fun onBackspaceClick() {
        if (pinCode.isEmpty()) return
        pinCode.pop()
        pinCodeSizeLiveData.value = pinCode.size
    }

    private fun checkPinCode() {
        val builder = StringBuilder()
        for (digit in pinCode) {
            builder.append(digit)
        }
        val pinCode = builder.toString()
        pinCodeCheckLiveData.value = pinCode
    }
}
