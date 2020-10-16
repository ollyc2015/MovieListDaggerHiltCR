package com.oliver_curtis.movies_list.common.processor

interface ViewProcessor<V> {

    fun attachView(view: V)

    fun detachView()
}