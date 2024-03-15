package com.mirash.familiar.activity.main.user

import com.mirash.familiar.model.user.IUser

/**
 * @author Mirash
 */
interface UserItemCallback {

    fun onItemClick(item: IUser)
    fun onEditClick(item: IUser)
}
