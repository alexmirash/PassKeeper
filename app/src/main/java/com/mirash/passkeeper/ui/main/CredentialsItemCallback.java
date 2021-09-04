package com.mirash.passkeeper.ui.main;

import com.mirash.passkeeper.drag.OnStartDragListener;

/**
 * @author Mirash
 */
public interface CredentialsItemCallback extends OnStartDragListener {
    void onLinkClick(String link);

    void onEditClick(int id);
}
