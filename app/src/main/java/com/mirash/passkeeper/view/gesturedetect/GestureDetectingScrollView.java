package com.mirash.passkeeper.view.gesturedetect;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mirash.passkeeper.view.gesturedetect.tool.GestureEventDetector;
import com.mirash.passkeeper.view.gesturedetect.tool.SingleTapUpListener;

/**
 * @author Mirash
 */
public class GestureDetectingScrollView extends ScrollView {
    private GestureEventDetector gestureDetector;

    public GestureDetectingScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GestureDetectingScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        gestureDetector = new GestureEventDetector(getContext());
    }

    public void setSingleTapListener(SingleTapUpListener singleTapListener) {
        gestureDetector.setSingleTapListener(singleTapListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean scrollResult = super.onTouchEvent(ev);
        boolean gestureResult = gestureDetector.onTouchEvent(ev);
        return scrollResult || gestureResult;
    }
}
