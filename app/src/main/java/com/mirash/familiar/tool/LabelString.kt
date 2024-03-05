package com.mirash.familiar.tool

/**
 * @author Mirash
 */
class LabelString(label: String, value: String?) : CharSequence {
    private val str: String = if (value.isNullOrEmpty()) "" else "$label: $value"
    override val length: Int
        get() = str.length

    override fun get(index: Int): Char = str[index]

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence = str.subSequence(startIndex, endIndex)

    override fun toString(): String = str
}
