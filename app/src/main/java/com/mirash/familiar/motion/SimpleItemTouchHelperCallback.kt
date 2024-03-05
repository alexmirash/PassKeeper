package com.mirash.familiar.motion

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

/**
 * @author Mirash
 */
class SimpleItemTouchHelperCallback(private val adapter: ItemTouchHelperAdapter) : ItemTouchHelper.Callback() {
    var touchStateCallback: ItemTouchStateCallback? = null
    private val alphaFull = 1.0f
    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        // Set movement flags based on the layout manager
//        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
//            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
//            final int swipeFlags = 0;
//            return makeMovementFlags(dragFlags, swipeFlags);
//        } else {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
        //        }
    }

    override fun onMove(
        recyclerView: RecyclerView, source: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
    ): Boolean {
        return if (source.itemViewType != target.itemViewType) false else adapter.onItemMove(
            source.getAdapterPosition(), target.getAdapterPosition()
        )
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
        adapter.onItemDismiss(viewHolder.getAdapterPosition())
    }

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            // Fade out the view as it is swiped out of the parent's bounds
            val alpha = (alphaFull - abs(dX.toDouble()) / viewHolder.itemView.width.toFloat()).toFloat()
            viewHolder.itemView.setAlpha(alpha)
            viewHolder.itemView.translationX = dX
        } else {
            super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        // We only want the active item to change
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder is ItemTouchStateCallback) {
                // Let the view holder know that this item is being moved or dragged
                viewHolder.onItemSelected()
                touchStateCallback?.onItemSelected()
            }
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.setAlpha(alphaFull)
        if (viewHolder is ItemTouchStateCallback) {
            // Tell the view holder it's time to restore the idle state
            viewHolder.onItemClear()
            touchStateCallback?.onItemClear()
        }
    }
}