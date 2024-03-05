package com.mirash.familiar.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * @author Mirash
 */
class SquareFrameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
        MeasureSpec.getSize(widthMeasureSpec).let {
            setMeasuredDimension(it, it)
        }

    }
}