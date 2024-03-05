package com.mirash.familiar.eventmanager.event

/**
 * @author Mirash
 */

class DataEvent<T>(
    eventType: IEventType,
    val data: T,
    owner: Any? = null,
    synchronous: Boolean = false,
    notifyToMainThread: Boolean = false
) : Event(eventType, owner, synchronous, notifyToMainThread) {

    /**
     *  just for Java
     */
    constructor(eventType: IEventType, data: T) : this(eventType, data, null, false, false)
}