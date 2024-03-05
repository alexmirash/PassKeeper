package com.mirash.familiar.activity.main.adapter

import com.mirash.familiar.model.CredentialsItem
import com.mirash.familiar.motion.OnStartDragListener

/**
 * @author Mirash
 */
interface CredentialsItemCallback : OnStartDragListener {
    fun onLinkClick(link: String)
    fun onEditClick(item: CredentialsItem)
    fun onOrderChanged(items: List<CredentialsItem>)
    fun onShare(item: CredentialsItem)
}
