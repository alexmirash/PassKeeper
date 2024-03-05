package com.mirash.familiar.eventmanager.subscription

import com.mirash.familiar.eventmanager.IEventObserver
import com.mirash.familiar.eventmanager.event.IEvent
import com.mirash.familiar.eventmanager.event.IEventType

/**
 * @author Mirash
 */
class MultiEventSubscription(
    private val eventTypes: List<IEventType>,
    observer: IEventObserver<IEvent>,
    isSingleTimeEvent: Boolean = false,
    owner: Any? = null
) : Subscription(observer, isSingleTimeEvent, owner) {

    override fun unsubscribe() {
        for (eventType in eventTypes) {
            unsubscribe(eventType)
        }
    }
}