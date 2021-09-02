package com.mirash.passkeeper.tool.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mirash.passkeeper.R;

/**
 * @author Mirash
 */
public class DividerListItemDecoration extends RecyclerView.ItemDecoration {
    private final Drawable dividerDrawable;

    public DividerListItemDecoration(Context context) {
        this(context, R.drawable.list_divider);
    }

    public DividerListItemDecoration(Context context, @DrawableRes int dividerResId) {
        dividerDrawable = ContextCompat.getDrawable(context, dividerResId);
    }

    @Override
    public void onDrawOver(@NonNull Canvas canvas, RecyclerView parent, @NonNull RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + dividerDrawable.getIntrinsicHeight();
            dividerDrawable.setBounds(left, top, right, bottom);
            dividerDrawable.draw(canvas);
        }
    }
}