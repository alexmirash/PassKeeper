package com.mirash.familiar.model.user

/**
 * @author Mirash
 */
open class UserModel(override var name: String) : IUser {
    override val id: Long = 0
}