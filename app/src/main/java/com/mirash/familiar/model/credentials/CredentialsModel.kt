package com.mirash.familiar.model.credentials

import com.mirash.familiar.tool.isAlike
import kotlinx.serialization.Serializable

/**
 * @author Mirash
 */
@Serializable
open class CredentialsModel : ICredentials {
    override var title: String? = null
    override var link: String? = null
    override var login: String? = null
    override var email: String? = null
    override var phone: String? = null
    override var password: String? = null
    override var pin: String? = null
    override var details: String? = null
    override var position = 0
    override val id: Long = 0

    override fun toString(): String {
        return "[$position]{title='$title', link='$link', login='$login', email='$email', phone='$phone', password='$password', pin='$pin', details='$details'}"
    }

    override fun isAlike(query: String): Boolean {
        return title.isAlike(query)
                || link.isAlike(query)
                || login.isAlike(query)
                || email.isAlike(query)
                || phone.isAlike(query)
    }
}
