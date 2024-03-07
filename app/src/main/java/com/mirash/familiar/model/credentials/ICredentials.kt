package com.mirash.familiar.model.credentials

import com.mirash.familiar.tool.listener.Filterable

/**
 * @author Mirash
 */
interface ICredentials : Filterable {
    val title: String?
    val link: String?
    val login: String?
    val email: String?
    val phone: String?
    val password: String?
    val pin: String?
    val details: String?
    val id: Long
    val position: Int
}
