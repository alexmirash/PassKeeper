package com.mirash.passkeeper.motion;

/**
 * @author Mirash
 */
public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}