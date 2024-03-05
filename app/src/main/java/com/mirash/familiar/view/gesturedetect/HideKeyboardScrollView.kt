package com.mirash.familiar.view.gesturedetect

import android.content.Context
import android.util.AttributeSet
import com.mirash.familiar.tool.hideKeyboard
import com.mirash.familiar.view.gesturedetect.tool.SingleTapUpListener

/**
 * @author Mirash
 */
class HideKeyboardScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : GestureDetectingScrollView(context, attrs, defStyleAttr), SingleTapUpListener {

    init {
        setSingleTapListener(this)
    }

    override fun onSingleTapUp(): Boolean {
        hideKeyboard(context)
        return false
    }
}
