package com.oliver_curtis.movies_list.common.viewmodel.livedata

import androidx.lifecycle.MutableLiveData
import com.oliver_curtis.movies_list.common.viewmodel.CallResult

class DefaultLiveDataProvider : LiveDataProvider {
    override fun <T> liveDataInstance(): MutableLiveData<CallResult<T>> = MutableLiveData()

}