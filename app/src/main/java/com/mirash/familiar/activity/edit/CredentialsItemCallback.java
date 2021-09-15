package com.mirash.familiar.activity.edit;

import com.mirash.familiar.motion.OnStartDragListener;
import com.mirash.familiar.model.CredentialsItem;

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
