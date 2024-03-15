package com.mirash.familiar.model.user

/**
 * @author Mirash
 */
interface IUser {
    val id: Long
    val name: String
    val email: String?
    val phone: String?
}
