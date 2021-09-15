package com.mirash.familiar.tool.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Mirash
 */
public class VerticalBottomSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int bottomSpace;

    public VerticalBottomSpaceItemDecoration(int bottomSpace) {
        this.bottomSpace = bottomSpace;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        RecyclerView.Adapter<?> adapter = parent.getAdapter();
        if (adapter != null && parent.getChildAdapterPosition(view) == adapter.getItemCount() - 1) {
            outRect.bottom = bottomSpace;
        }
    }
}