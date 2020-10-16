package com.oliver_curtis.movies_list.common.viewmodel.livedata

import androidx.lifecycle.MutableLiveData
import com.oliver_curtis.movies_list.common.viewmodel.CallResult

interface LiveDataProvider {
    fun <T> liveDataInstance(): MutableLiveData<CallResult<T>>

}

interface LiveDataObserver {

    fun <T> MutableLiveData<T>.notifyObserver()
}