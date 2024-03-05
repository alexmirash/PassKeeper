package com.mirash.familiar.view

import android.content.Context
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.TextView.OnEditorActionListener
import com.google.android.material.textfield.TextInputLayout

/**
 * @author Mirash
 */
class StyledTextInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextInputLayout(context, attrs, defStyleAttr) {

    private var isDisabled = false

    var text: String?
        get() = editText?.getText().toString()
        set(text) {
            editText?.setText(text)
        }

    fun setTextWithNoAnimation(value: String?) {
        val isAnimationEnabled = isHintAnimationEnabled
        isHintAnimationEnabled = false
        text = value
        isHintAnimationEnabled = isAnimationEnabled
    }

    fun setOnEditorActionListener(listener: OnEditorActionListener?) {
        editText?.setOnEditorActionListener(listener)
    }

    fun setEditTextOnFocusChangeListener(listener: OnFocusChangeListener?) {
        editText?.onFocusChangeListener = listener
    }

    fun addTextChangedListener(textWatcher: TextWatcher?) {
        editText?.addTextChangedListener(textWatcher)
    }

    fun removeTextChangeListener(textWatcher: TextWatcher?) {
        editText?.removeTextChangedListener(textWatcher)
    }

    fun setInputTypeNull() {
        editText?.setInputType(InputType.TYPE_NULL)
        isDisabled = true
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean = isDisabled || super.dispatchTouchEvent(event)
}
