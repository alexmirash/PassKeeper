package com.mirash.passkeeper.drag;

import android.graphics.Canvas;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Mirash
 */
public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {
    public static final float ALPHA_FULL = 1.0f;
    private final ItemTouchHelperAdapter adapter;
    private ItemTouchStateCallback touchStateCallback;

    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        // Set movement flags based on the layout manager
//        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
//            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
//            final int swipeFlags = 0;
//            return makeMovementFlags(dragFlags, swipeFlags);
//        } else {
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
//        }
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        if (source.getItemViewType() != target.getItemViewType()) return false;
        return adapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        adapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            // Fade out the view as it is swiped out of the parent's bounds
            final float alpha = ALPHA_FULL - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        } else {
            super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        // We only want the active item to change
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof ItemTouchStateCallback) {
                // Let the view holder know that this item is being moved or dragged
                ItemTouchStateCallback itemViewHolder = (ItemTouchStateCallback) viewHolder;
                itemViewHolder.onItemSelected();
                if (touchStateCallback != null) touchStateCallback.onItemSelected();
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setAlpha(ALPHA_FULL);
        if (viewHolder instanceof ItemTouchStateCallback) {
            // Tell the view holder it's time to restore the idle state
            ItemTouchStateCallback itemViewHolder = (ItemTouchStateCallback) viewHolder;
            itemViewHolder.onItemClear();
            if (touchStateCallback != null) touchStateCallback.onItemClear();
        }
    }

    public void setTouchStateCallback(ItemTouchStateCallback touchStateCallback) {
        this.touchStateCallback = touchStateCallback;
    }
}