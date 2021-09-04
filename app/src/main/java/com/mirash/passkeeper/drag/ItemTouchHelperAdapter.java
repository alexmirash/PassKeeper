package com.mirash.passkeeper.drag;

/**
 * @author Mirash
 */
public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}