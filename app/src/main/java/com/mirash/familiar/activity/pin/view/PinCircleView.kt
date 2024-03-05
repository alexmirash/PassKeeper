package com.mirash.familiar.activity.pin.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.widget.Checkable
import android.widget.FrameLayout
import android.widget.ImageView
import com.mirash.familiar.R

/**
 * @author Mirash
 */
class PinCircleView(context: Context) : FrameLayout(context), Checkable {
    private var isChecked = false
    private var checkedImageView: ImageView
    private var idleImageView: ImageView
    private var animator: ObjectAnimator

    init {
        idleImageView = ImageView(context)
        idleImageView.setImageResource(R.drawable.ic_circle_outline)
        checkedImageView = ImageView(context)
        checkedImageView.setImageResource(R.drawable.ic_circle_filled)
        checkedImageView.imageAlpha = 0
        addView(idleImageView)
        addView(checkedImageView)
        animator = ObjectAnimator().setDuration(250)

        ObjectAnimator().setDuration(250).let {
            it.addUpdateListener { valueAnimator: ValueAnimator ->
                checkedImageView.imageAlpha = valueAnimator.getAnimatedValue() as Int
            }
            animator = it
        }
    }

    override fun setChecked(checked: Boolean) {
        if (isChecked != checked) {
            isChecked = checked
            if (checked) {
                animator.setIntValues(0, 255)
            } else {
                animator.setIntValues(255, 0)
            }
            animator.start()
        }
    }

    override fun isChecked(): Boolean {
        return isChecked
    }

    override fun toggle() {
        setChecked(!isChecked)
    }
}
