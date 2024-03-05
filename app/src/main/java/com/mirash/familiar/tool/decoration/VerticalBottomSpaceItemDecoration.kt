package com.mirash.familiar.tool.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * @author Mirash
 */
class VerticalBottomSpaceItemDecoration(private val bottomSpace: Int) : ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        parent.adapter?.let {
            if (parent.getChildAdapterPosition(view) == it.itemCount - 1) {
                outRect.bottom = bottomSpace
            }
        }
    }
}