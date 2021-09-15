package com.mirash.familiar.activity.pin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mirash.familiar.Const;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mirash
 */
public class PinIndicatorView extends LinearLayout {
    private List<PinCircleView> pinIndicatorViews;

    public PinIndicatorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PinIndicatorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        int size = Const.PIN_CODE_SIZE;
        setWeightSum(size);
        pinIndicatorViews = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            PinCircleView pinCircleView = new PinCircleView(getContext());
            pinIndicatorViews.add(pinCircleView);
            addView(pinCircleView, new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        }
    }

    public void setCheckedSize(int checkedSize) {
        for (int i = 0; i < pinIndicatorViews.size(); i++) {
            pinIndicatorViews.get(i).setChecked(i < checkedSize);
        }
    }
}
