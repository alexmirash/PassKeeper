package com.mirash.familiar.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.IdRes
import com.mirash.familiar.R

/**
 * @author Mirash
 */
class PinInputView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {
    private var callback: PinCallback? = null

    init {
        orientation = VERTICAL
        inflate(context, R.layout.pin_input_view, this)
        @IdRes val buttonIds = intArrayOf(
            R.id.pin_button_0,
            R.id.pin_button_1,
            R.id.pin_button_2,
            R.id.pin_button_3,
            R.id.pin_button_4,
            R.id.pin_button_5,
            R.id.pin_button_6,
            R.id.pin_button_7,
            R.id.pin_button_8,
            R.id.pin_button_9
        )
        for (i in buttonIds.indices) {
            initInputButton(buttonIds[i], i)
        }
        findViewById<View>(R.id.pin_button_backspace).setOnClickListener { _: View -> callback?.onBackspaceClick() }
    }

    private fun initInputButton(@IdRes buttonId: Int, digit: Int) {
        findViewById<View>(buttonId).setOnClickListener { _: View -> callback?.onDigitClick(digit) }
    }

    fun initBiometricButton(clickListener: OnClickListener?) {
        val bioButton = findViewById<TextView>(R.id.pin_button_bio)
        bioButton.visibility = VISIBLE
        bioButton.setOnClickListener(clickListener)
    }

    fun setCallback(callback: PinCallback?) {
        this.callback = callback
    }

    interface PinCallback {
        fun onDigitClick(digit: Int)
        fun onBackspaceClick()
    }
}