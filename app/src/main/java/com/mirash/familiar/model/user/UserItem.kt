package com.mirash.familiar.model.user

import com.mirash.familiar.user.UserControl

/**
 * @author Mirash
 */
class UserItem(private val user: IUser, var isChecked: Boolean = user.id == UserControl.userId) : IUser {
    override val id: Long
        get() = user.id
    override val name: String
        get() = user.name
    override val email: String?
        get() = user.email
    override val phone: String?
        get() = user.phone
}