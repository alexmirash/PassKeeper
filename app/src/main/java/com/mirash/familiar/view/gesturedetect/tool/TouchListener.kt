package com.mirash.familiar.view.gesturedetect.tool

import android.view.MotionEvent

/**
 * @author Mirash
 */
interface TouchListener {
    fun onTouchEvent(event: MotionEvent): Boolean
}
