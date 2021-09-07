package com.mirash.passkeeper.activity.pin.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.mirash.passkeeper.R;

/**
 * @author Mirash
 */
public class PinCircleView extends FrameLayout implements Checkable {
    private boolean isChecked;
    private ImageView checkedImageView;
    private ImageView idleImageView;
    private ObjectAnimator animator;

    public PinCircleView(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        idleImageView = new ImageView(getContext());
        idleImageView.setImageResource(R.drawable.ic_circle_outline);
        checkedImageView = new ImageView(getContext());
        checkedImageView.setImageResource(R.drawable.ic_circle_filled);
        checkedImageView.setImageAlpha(0);
        addView(idleImageView);
        addView(checkedImageView);
        animator = new ObjectAnimator().setDuration(250);
        animator.addUpdateListener(valueAnimator -> {
            int value = (int) valueAnimator.getAnimatedValue();
            checkedImageView.setImageAlpha(value);
        });
    }


    @Override
    public void setChecked(boolean checked) {
        if (isChecked != checked) {
            isChecked = checked;
            if (checked) {
                animator.setIntValues(0, 255);
            } else {
                animator.setIntValues(255, 0);
            }
            animator.start();

        }
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }
}
