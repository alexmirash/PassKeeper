package com.mirash.familiar.activity.main.credentials

import com.mirash.familiar.model.credentials.ICredentials
import com.mirash.familiar.motion.OnStartDragListener

/**
 * @author Mirash
 */
interface CredentialsItemCallback : OnStartDragListener {
    fun onLinkClick(link: String)
    fun onEditClick(item: ICredentials)
    fun onOrderChanged(items: List<ICredentials>)
}
