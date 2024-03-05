package com.mirash.familiar.view.gesturedetect.tool

/**
 * @author Mirash
 */
class TapDetector(private val tapUpListener: SingleTapUpListener) {
    fun onSingleTapUp(): Boolean = tapUpListener.onSingleTapUp()
}