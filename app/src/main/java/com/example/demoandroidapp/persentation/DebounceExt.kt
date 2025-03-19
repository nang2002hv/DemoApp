package com.example.demoandroidapp.persentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.AbstractCoroutine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DebounceExt {
}

fun <T> LiveData<T>.debounce(duration: Long, coroutine: CoroutineScope) : LiveData<T> {
    val mediatorLiveData = MediatorLiveData<T>()
    var job : Job?= null
    mediatorLiveData.addSource(this) {value ->
        job?.cancel()
        job = coroutine.launch {
            delay(timeMillis = duration)
            mediatorLiveData.value = value
        }
    }
    return mediatorLiveData

}