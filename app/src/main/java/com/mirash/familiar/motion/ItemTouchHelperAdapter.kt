package com.mirash.familiar.motion

/**
 * @author Mirash
 */
interface ItemTouchHelperAdapter {
    fun onMoved(fromPosition: Int, toPosition: Int): Boolean
    fun onSwiped(position: Int)
}