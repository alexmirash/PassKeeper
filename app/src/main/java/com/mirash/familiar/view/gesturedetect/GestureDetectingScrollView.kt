package com.mirash.familiar.view.gesturedetect

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView
import com.mirash.familiar.view.gesturedetect.tool.GestureEventDetector
import com.mirash.familiar.view.gesturedetect.tool.SingleTapUpListener

/**
 * @author Mirash
 */
open class GestureDetectingScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ScrollView(context, attrs, defStyleAttr) {
    private val gestureDetector: GestureEventDetector = GestureEventDetector(context)


    fun setSingleTapListener(singleTapListener: SingleTapUpListener?) {
        gestureDetector.setSingleTapListener(singleTapListener)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return super.onTouchEvent(ev) or gestureDetector.onTouchEvent(ev)
    }
}
