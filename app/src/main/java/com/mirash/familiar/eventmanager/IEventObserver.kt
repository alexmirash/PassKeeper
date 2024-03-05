package com.mirash.familiar.eventmanager

import com.mirash.familiar.eventmanager.event.IEvent


/**
 * @author Mirash
 */
interface IEventObserver<T : IEvent> {
    fun onNotify(event: T)
}
