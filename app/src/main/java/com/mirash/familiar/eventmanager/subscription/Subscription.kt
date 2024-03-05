package com.mirash.familiar.eventmanager.subscription

import com.mirash.familiar.eventmanager.EventManager
import com.mirash.familiar.eventmanager.IEventObserver
import com.mirash.familiar.eventmanager.event.IEvent
import com.mirash.familiar.eventmanager.event.IEventType
import java.lang.ref.WeakReference

/**
 * @author Mirash
 */
abstract class Subscription(
    private val observer: IEventObserver<IEvent>,
    val isSingleTimeEvent: Boolean,
    owner: Any?
) : IEventObserver<IEvent> {
    private val owner: WeakReference<Any>? =
        if (owner == null) null else WeakReference(owner)

    abstract fun unsubscribe()

    fun unsubscribe(eventType: IEventType) {
        EventManager.unsubscribe(eventType, this)
    }

    override fun onNotify(event: IEvent) {
        if (owner == null) {
            observer.onNotify(event)
        } else {
            val obsOwner: Any = owner.get() ?: return
            if (obsOwner == event.owner) observer.onNotify(event)
        }
    }

    fun <T : IEventObserver<out IEvent>> getObserver(): T {
        return observer as T
    }
}