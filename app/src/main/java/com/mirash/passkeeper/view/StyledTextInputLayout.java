package com.mirash.passkeeper.view;

import android.content.Context;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

/**
 * @author Mirash
 */

@SuppressWarnings("ConstantConditions")
public class StyledTextInputLayout extends TextInputLayout {
    private boolean isDisabled;

    public StyledTextInputLayout(Context context) {
        super(context, null);
    }

    public StyledTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StyledTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setText(String text) {
        getEditText().setText(text);
    }

    public void setTextWithNoAnimation(String text) {
        boolean isAnimationEnabled = isHintAnimationEnabled();
        setHintAnimationEnabled(false);
        setText(text);
        setHintAnimationEnabled(isAnimationEnabled);
    }

    public void setOnEditorActionListener(TextView.OnEditorActionListener listener) {
        getEditText().setOnEditorActionListener(listener);
    }

    public void setEditTextOnFocusChangeListener(OnFocusChangeListener listener) {
        getEditText().setOnFocusChangeListener(listener);
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        getEditText().addTextChangedListener(textWatcher);
    }

    public void removeTextChangeListener(TextWatcher textWatcher) {
        getEditText().removeTextChangedListener(textWatcher);
    }

    public String getText() {
        return getEditText().getText().toString();
    }

    public void setInputTypeNull() {
        getEditText().setInputType(InputType.TYPE_NULL);
        isDisabled = true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return isDisabled || super.dispatchTouchEvent(ev);
    }
}
