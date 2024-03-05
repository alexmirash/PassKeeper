package com.mirash.familiar.tool.listener

/**
 * @author Mirash
 */
interface Filterable {
    fun isAlike(query: String): Boolean
}
