package com.mirash.familiar.eventmanager.event

/**
 * @author Mirash
 */
open class Event(
    private val eventType: IEventType,
    override val owner: Any? = null,
    override val isSynchronous: Boolean = false,
    override val isNotifyToMainThread: Boolean = false
) : IEvent {
    override fun getEventType() = eventType
}