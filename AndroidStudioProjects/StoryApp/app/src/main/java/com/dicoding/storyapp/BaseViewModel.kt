package com.dicoding.storyapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.storyapp.utils.EventMessage

abstract class BaseViewModel : ViewModel() {

    // Mutable/LiveData of String resource reference Event
    private val _message = MutableLiveData<EventMessage<String>>()
    val message: LiveData<EventMessage<String>>
        get() = _message

    // Post in background thread
    fun postMessage(message: String) {
        _message.postValue(EventMessage(message))
    }

    // Post in main thread
    fun setMessage(message: String) {
        _message.value = EventMessage(message)
    }

    // Mutable/LiveData of busy status
    private val _isBusy = MutableLiveData(false)
    val isBusy: LiveData<Boolean>
        get() = _isBusy

    // Post in background thread
    fun postBusy(isBusy: Boolean) {
        _isBusy.postValue(isBusy)
    }

    // Post in main thread
    fun setBusy(isBusy: Boolean) {
        _isBusy.value = isBusy
    }

    // Mutable/LiveData of loading status
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    // Post in background thread
    fun postLoading(isLoading: Boolean) {
        _isLoading.postValue(isLoading)
    }

    // Post in main thread
    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
}