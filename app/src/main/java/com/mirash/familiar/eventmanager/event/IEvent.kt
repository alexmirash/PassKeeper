package com.mirash.familiar.eventmanager.event

/**
 * @author Mirash
 */
interface IEvent {
    fun getEventType(): IEventType

    val owner: Any?

    val isSynchronous: Boolean
        get() = false
    val isNotifyToMainThread: Boolean
        get() = false
}