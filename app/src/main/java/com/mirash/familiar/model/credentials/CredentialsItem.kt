package com.mirash.familiar.model.credentials

import kotlinx.serialization.Serializable

/**
 * @author Mirash
 */
@Serializable
class CredentialsItem(private val credentials: ICredentials) : ICredentials {
    var isPasswordVisible = false

    override val title: String?
        get() = credentials.title
    override val link: String?
        get() = credentials.link
    override val login: String?
        get() = credentials.login
    override val email: String?
        get() = credentials.email
    override val phone: String?
        get() = credentials.phone
    override val password: String?
        get() = credentials.password
    override val pin: String?
        get() = credentials.pin
    override val details: String?
        get() = credentials.details
    override val id: Long
        get() = credentials.id
    override val position: Int
        get() = credentials.position

    override fun toString(): String {
        return credentials.toString()
    }

    override fun isAlike(query: String): Boolean {
        return credentials.isAlike(query)
    }
}
