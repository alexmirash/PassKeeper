package com.mirash.familiar.tool.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.mirash.familiar.R

/**
 * @author Mirash
 */
class DividerListItemDecoration(
    context: Context,
    @DrawableRes dividerResId: Int = R.drawable.list_divider
) : ItemDecoration() {
    private val dividerDrawable: Drawable? = ContextCompat.getDrawable(context, dividerResId)

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.getPaddingLeft()
        val right = parent.width - parent.getPaddingRight()
        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            val top = child.bottom + (child.layoutParams as RecyclerView.LayoutParams).bottomMargin
            dividerDrawable?.let {
                val bottom = top + it.intrinsicHeight
                it.setBounds(left, top, right, bottom)
                it.draw(canvas)
            }
        }
    }
}