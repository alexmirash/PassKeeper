package com.mirash.familiar.motion

import android.graphics.Canvas
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Mirash
 */
class CredentialsItemTouchHelperCallback(private val adapter: ItemTouchHelperAdapter) :
    ItemTouchHelper.Callback() {
    var touchStateCallback: ItemTouchStateCallback? = null
    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView, source: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
    ): Boolean {
        return true //source.itemViewType == target.itemViewType
    }

    override fun onMoved(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        fromPos: Int,
        target: RecyclerView.ViewHolder,
        toPos: Int,
        x: Int,
        y: Int
    ) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y)
        Log.d("LOL", "onMoved: $fromPos -> $toPos")
        adapter.onMoved(fromPos, toPos)
    }


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.onSwiped(viewHolder.getAdapterPosition())
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
//            val alpha = (alphaFull - abs(dX.toDouble()) / viewHolder.itemView.width).toFloat()
//            viewHolder.itemView.setAlpha(alpha)
            viewHolder.itemView.translationX = dX
        } else {
            super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        // We only want the active item to change
        val name = when (actionState) {
            ItemTouchHelper.ACTION_STATE_IDLE -> {
                "IDLE"
            }

            ItemTouchHelper.ACTION_STATE_DRAG -> {
                "DRAG"
            }

            ItemTouchHelper.ACTION_STATE_SWIPE -> {
                "SWIPE"
            }

            else -> {
                "UNKNOWN_$actionState"
            }
        }
        Log.d("LOL", "onSelectedChanged: $name")
        val selected = actionState == ItemTouchHelper.ACTION_STATE_DRAG
        if (viewHolder is ItemTouchStateCallback) {
            viewHolder.onItemSelectStateChanged(selected)
        }
        touchStateCallback?.onItemSelectStateChanged(selected)
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        if (viewHolder is ItemTouchStateCallback) {
            Log.d("LOL", "clearView")
            // Tell the view holder it's time to restore the idle state
            viewHolder.onItemClear()
        }
        touchStateCallback?.onItemClear()
    }
}