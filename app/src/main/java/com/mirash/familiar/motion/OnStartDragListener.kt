package com.mirash.familiar.motion

import androidx.recyclerview.widget.RecyclerView

/**
 * @author Mirash
 */
interface OnStartDragListener {
    fun onDragStart(viewHolder: RecyclerView.ViewHolder)
}