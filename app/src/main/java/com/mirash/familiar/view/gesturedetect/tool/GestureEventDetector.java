package com.mirash.familiar.view.gesturedetect.tool;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

/**
 * @author Mirash
 */
public class GestureEventDetector implements TouchListener {
    private final GestureDetector gestureDetector;
    private boolean isEnabled;
    private TapDetector tapDetector;

    public GestureEventDetector(Context context) {
        gestureDetector = new GestureDetector(context, new GestureListener());
        isEnabled = true;
    }

    public void setSingleTapListener(SingleTapUpListener tapListener) {
        tapDetector = new TapDetector(tapListener);
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    private final class GestureListener extends SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return tapDetector != null && tapDetector.onSingleTapUp();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return isEnabled && gestureDetector.onTouchEvent(event);
    }
}