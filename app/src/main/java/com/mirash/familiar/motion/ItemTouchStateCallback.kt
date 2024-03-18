package com.mirash.familiar.motion

/**
 * @author Mirash
 */
interface ItemTouchStateCallback {
    fun onItemSelectStateChanged(selected: Boolean)
    fun onItemClear()
}