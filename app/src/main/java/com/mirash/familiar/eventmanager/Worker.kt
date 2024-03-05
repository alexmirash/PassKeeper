package com.mirash.familiar.eventmanager

import android.os.ConditionVariable

/**
 * @author Mirash
 */
abstract class Worker : Runnable {
    private val done = ConditionVariable(false)
    override fun run() {
        work()
        done.open()
    }

    fun block() {
        done.block()
    }

    abstract fun work()
}