package com.mirash.familiar.activity.pin.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import com.mirash.familiar.tool.PIN_CODE_SIZE

/**
 * @author Mirash
 */
class PinIndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val pinIndicatorViews: MutableList<PinCircleView>

    init {
        orientation = HORIZONTAL
        val size = PIN_CODE_SIZE
        weightSum = size.toFloat()
        pinIndicatorViews = ArrayList(size)
        for (i in 0 until size) {
            val pinCircleView = PinCircleView(context)
            pinIndicatorViews.add(pinCircleView)
            addView(pinCircleView, LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f))
        }
    }

    fun setCheckedSize(checkedSize: Int) {
        for (i in pinIndicatorViews.indices) {
            pinIndicatorViews[i].isChecked = i < checkedSize
        }
    }
}
