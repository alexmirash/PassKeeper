package com.mirash.passkeeper.view.gesturedetect.tool;

/**
 * @author Mirash
 */
public class TapDetector {
    private final SingleTapUpListener tapUpListener;

    public TapDetector(SingleTapUpListener tapUpListener) {
        this.tapUpListener = tapUpListener;
    }

    public boolean onSingleTapUp() {
        return tapUpListener.onSingleTapUp();
    }
}