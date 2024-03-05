package com.mirash.familiar.view.gesturedetect.tool

import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent

/**
 * @author Mirash
 */
class GestureEventDetector(context: Context) : TouchListener {
    private val gestureDetector: GestureDetector = GestureDetector(context, GestureListener())
    private var tapDetector: TapDetector? = null
    var isEnabled: Boolean = true

    fun setSingleTapListener(tapListener: SingleTapUpListener?) {
        if (tapListener != null) {
            tapDetector = TapDetector(tapListener)
        } else {
            tapDetector = null
        }
    }

    private inner class GestureListener : SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean = true

        override fun onSingleTapUp(e: MotionEvent): Boolean = tapDetector?.onSingleTapUp() ?: false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean = isEnabled && gestureDetector.onTouchEvent(event)
}