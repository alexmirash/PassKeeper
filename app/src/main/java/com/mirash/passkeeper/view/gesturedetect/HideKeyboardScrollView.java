package com.mirash.passkeeper.view.gesturedetect;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mirash.passkeeper.tool.Utils;
import com.mirash.passkeeper.view.gesturedetect.tool.SingleTapUpListener;

/**
 * @author Mirash
 */
public class HideKeyboardScrollView extends GestureDetectingScrollView implements SingleTapUpListener {
    public HideKeyboardScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HideKeyboardScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        super.init();
        setSingleTapListener(this);
    }

    @Override
    public boolean onSingleTapUp() {
        Utils.hideKeyboard(getContext());
        return false;
    }
}
