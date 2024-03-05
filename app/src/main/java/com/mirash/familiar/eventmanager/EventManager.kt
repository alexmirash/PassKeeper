package com.mirash.familiar.eventmanager

import android.os.Handler
import android.os.Looper
import com.mirash.familiar.eventmanager.event.Event
import com.mirash.familiar.eventmanager.event.IEvent
import com.mirash.familiar.eventmanager.event.IEventType
import com.mirash.familiar.eventmanager.subscription.MultiEventSubscription
import com.mirash.familiar.eventmanager.subscription.SingleEventSubscription
import com.mirash.familiar.eventmanager.subscription.Subscription
import java.util.concurrent.CopyOnWriteArrayList


/**
 * @author Mirash
 */
object EventManager {
    private val handler = Handler(Looper.getMainLooper())
    private val observers: MutableMap<IEventType, MutableList<Subscription>> = HashMap()

    @Synchronized
    fun <T : IEvent> addObserver(
        eventTypes: List<IEventType>,
        observer: IEventObserver<T>,
        singleTime: Boolean = false,
        owner: Any? = null
    ): Subscription {
        val s = MultiEventSubscription(
            eventTypes, observer as IEventObserver<IEvent>, singleTime, owner
        )
        for (eventType in eventTypes) {
            addObserver(eventType, s)
        }
        return s
    }

    @Synchronized
    fun <T : IEvent> addObserver(
        eventType: IEventType,
        observer: IEventObserver<T>,
        singleTime: Boolean = false,
        owner: Any? = null
    ): Subscription {
        val s = SingleEventSubscription(
            eventType, observer as IEventObserver<IEvent>, singleTime, owner
        )
        addObserver(eventType, s)
        return s
    }

    private fun addObserver(eventType: IEventType, subscription: Subscription) {
        var list: MutableList<Subscription>? = observers[eventType]
        if (list == null) {
            //TODO consider this, probably synchronizing is better
            list = CopyOnWriteArrayList()
            observers[eventType] = list
        }
        list.add(subscription)
    }

    fun post(eventType: IEventType) {
        post(Event(eventType))
    }

    @Synchronized
    fun post(event: IEvent) {
        if (event.isNotifyToMainThread) {
            if (event.isSynchronous) {
                if (Looper.getMainLooper() == Looper.myLooper()) {
                    notifyObservers(event)
                } else {
                    val worker: Worker = object : Worker() {
                        override fun work() {
                            notifyObservers(event)
                        }
                    }
                    handler.post(worker)
                    worker.block()
                }
            } else {
                handler.post { notifyObservers(event) }
            }
        } else {
            notifyObservers(event)
        }
    }

    private fun notifyObservers(event: IEvent) {
        val list: MutableList<Subscription>? = observers[event.getEventType()]
        if (list != null) {
            val observersToRemove: MutableList<Subscription> = ArrayList(list.size)
            for (observer in list) {
                observer.onNotify(event)
                if (observer.isSingleTimeEvent) {
                    observersToRemove.add(observer)
                }
            }
            list.removeAll(observersToRemove)
        }
    }

    fun unsubscribe(eventType: IEventType, observer: Subscription) {
        Looper.myLooper()?.let {
            Handler(it).post {
                unsubscribeInternal(eventType, observer)
            }
        } ?: kotlin.run { handler.post { unsubscribeInternal(eventType, observer) } }
    }

    @Synchronized
    private fun unsubscribeInternal(eventType: IEventType, observer: Subscription) {
        val list: MutableList<Subscription>? = observers[eventType]
        list?.remove(observer)
    }

    fun hasSubscribers(event: IEventType) = !observers[event].isNullOrEmpty()
}

fun <T : IEvent> EventManager.subscribe(
    eventType: IEventType, singleTime: Boolean = false, owner: Any? = null, observer: (T) -> Unit
): Subscription {
    return addObserver(eventType, object : IEventObserver<T> {
        override fun onNotify(event: T) {
            observer(event)
        }
    }, singleTime, owner)
}

fun <T : IEvent> EventManager.subscribe(
    eventTypes: List<IEventType>,
    singleTime: Boolean = false,
    owner: Any? = null,
    observer: (T) -> Unit
): Subscription {
    return addObserver(eventTypes, object : IEventObserver<T> {
        override fun onNotify(event: T) {
            observer(event)
        }
    }, singleTime, owner)
}
