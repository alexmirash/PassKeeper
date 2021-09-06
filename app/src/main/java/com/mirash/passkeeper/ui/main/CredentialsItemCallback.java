package com.mirash.passkeeper.ui.main;

import com.mirash.passkeeper.drag.OnStartDragListener;
import com.mirash.passkeeper.model.CredentialsItem;

import java.util.List;

/**
 * @author Mirash
 */
public interface CredentialsItemCallback extends OnStartDragListener {
    void onLinkClick(String link);

    void onEditClick(CredentialsItem item);

    void onOrderChanged(List<CredentialsItem> items);

    void onShare(CredentialsItem item);
}
