package com.mirash.familiar.view

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton

/**
 * @author Mirash
 */
class PinMaterialButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : MaterialButton(context, attrs, defStyle) {

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
        MeasureSpec.getSize(widthMeasureSpec).let {
            setMeasuredDimension(it, it)
            cornerRadius = it / 2
        }
    }
}