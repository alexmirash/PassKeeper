package com.mirash.passkeeper.activity.edit;

import com.mirash.passkeeper.motion.OnStartDragListener;
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
