package com.example.flora1.core.presentation.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

fun <T> LiveData<T>.singleEvent(): LiveData<T> =
    SingleEventLiveData(this)

private class SingleEventLiveData<T>(
    val source: LiveData<T>,
) : LiveData<T>() {
    private val hasBeenObserved = AtomicBoolean(false)
    private val wrappers = mutableMapOf<Observer<in T>, Observer<in T>>()

    init {
        // When new value is set, set livedata as unobserved
        source.observeForever {
            hasBeenObserved.set(false)
        }
    }

    override fun observeForever(observer: Observer<in T>) {
        source.observeForever(wrap(observer))
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        source.observe(owner, wrap(observer))
    }

    override fun removeObserver(observer: Observer<in T>) {
        val wrapper = wrappers.remove(observer)
        if (wrapper != null)
            source.removeObserver(wrapper)
    }

    private fun wrap(observer: Observer<in T>): Observer<in T> {
        val wrapper = Observer<T> {
            // when the 1st observer is notified, set livedata as observed
            if (hasBeenObserved.compareAndSet(false, true))
                observer.onChanged(it)
        }
        wrappers[observer] = wrapper
        return wrapper
    }
}
