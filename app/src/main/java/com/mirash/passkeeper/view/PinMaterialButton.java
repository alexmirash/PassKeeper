package com.mirash.passkeeper.view;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.button.MaterialButton;

/**
 * @author Mirash
 */
public class PinMaterialButton extends MaterialButton {

    public PinMaterialButton(Context context) {
        super(context);
    }


    public PinMaterialButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public PinMaterialButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //noinspection SuspiciousNameCombination
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(size, size);
        setCornerRadius(size / 2);
    }
}