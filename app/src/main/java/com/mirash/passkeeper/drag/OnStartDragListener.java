package com.mirash.passkeeper.drag;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Mirash
 */
public interface OnStartDragListener {
    void onDragStart(RecyclerView.ViewHolder viewHolder);
}